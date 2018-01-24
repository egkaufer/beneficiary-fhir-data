package gov.hhs.cms.bluebutton.server.app.stu3.providers;

import java.util.Optional;

import org.hl7.fhir.dstu3.model.Address;
import org.hl7.fhir.dstu3.model.ExplanationOfBenefit;
import org.hl7.fhir.dstu3.model.ExplanationOfBenefit.BenefitBalanceComponent;
import org.hl7.fhir.dstu3.model.ExplanationOfBenefit.BenefitComponent;
import org.hl7.fhir.dstu3.model.ExplanationOfBenefit.ExplanationOfBenefitStatus;
import org.hl7.fhir.dstu3.model.ExplanationOfBenefit.ItemComponent;
import org.hl7.fhir.dstu3.model.Money;
import org.hl7.fhir.dstu3.model.Period;
import org.hl7.fhir.dstu3.model.TemporalPrecisionEnum;
import org.hl7.fhir.dstu3.model.UnsignedIntType;
import org.hl7.fhir.dstu3.model.codesystems.BenefitCategory;

import com.justdavis.karl.misc.exceptions.BadCodeMonkeyException;

import gov.hhs.cms.bluebutton.data.model.rif.HospiceClaim;
import gov.hhs.cms.bluebutton.data.model.rif.HospiceClaimLine;

/**
 * Transforms CCW {@link HospiceClaim} instances into FHIR
 * {@link ExplanationOfBenefit} resources.
 */
final class HospiceClaimTransformer {
	/**
	 * @param claim
	 *            the CCW {@link HospiceClaim} to transform
	 * @return a FHIR {@link ExplanationOfBenefit} resource that represents the
	 *         specified {@link HospiceClaim}
	 */
	static ExplanationOfBenefit transform(Object claim) {
		if (!(claim instanceof HospiceClaim))
			throw new BadCodeMonkeyException();
		return transformClaim((HospiceClaim) claim);
	}

	/**
	 * @param claimGroup
	 *            the CCW {@link HospiceClaim} to transform
	 * @return a FHIR {@link ExplanationOfBenefit} resource that represents the
	 *         specified {@link HospiceClaim}
	 */
	private static ExplanationOfBenefit transformClaim(HospiceClaim claimGroup) {
		ExplanationOfBenefit eob = new ExplanationOfBenefit();

		eob.setId(TransformerUtils.buildEobId(ClaimType.HOSPICE, claimGroup.getClaimId()));
		eob.addIdentifier().setSystem(TransformerConstants.CODING_CCW_CLAIM_ID)
				.setValue(claimGroup.getClaimId());
		eob.addIdentifier().setSystem(TransformerConstants.CODING_CCW_CLAIM_GROUP_ID)
				.setValue(claimGroup.getClaimGroupId().toPlainString());

		// map eob type codes into FHIR
		TransformerUtils.mapEobType(eob, ClaimType.HOSPICE, Optional.of(claimGroup.getNearLineRecordIdCode()), 
				Optional.of(claimGroup.getClaimTypeCode()));
		
		eob.getInsurance()
				.setCoverage(TransformerUtils.referenceCoverage(claimGroup.getBeneficiaryId(), MedicareSegment.PART_A));
		eob.setPatient(TransformerUtils.referencePatient(claimGroup.getBeneficiaryId()));
		eob.setStatus(ExplanationOfBenefitStatus.ACTIVE);

		TransformerUtils.validatePeriodDates(claimGroup.getDateFrom(), claimGroup.getDateThrough());
		TransformerUtils.setPeriodStart(eob.getBillablePeriod(), claimGroup.getDateFrom());
		TransformerUtils.setPeriodEnd(eob.getBillablePeriod(), claimGroup.getDateThrough());

		// set the provider number which is common among several claim types
		TransformerUtils.setProviderNumber(eob, claimGroup.getProviderNumber());

		eob.getPayment()
				.setAmount((Money) new Money().setSystem(TransformerConstants.CODED_MONEY_USD)
						.setValue(claimGroup.getPaymentAmount()));

		if (claimGroup.getPatientStatusCd().isPresent()) {
			eob.addInformation().setCategory(
					TransformerUtils.createCodeableConcept(TransformerConstants.CODING_CCW_PATIENT_STATUS,
							String.valueOf(claimGroup.getPatientStatusCd().get())));
		}

		BenefitBalanceComponent benefitBalances = new BenefitBalanceComponent(
				TransformerUtils.createCodeableConcept(
						TransformerConstants.CODING_FHIR_BENEFIT_BALANCE, BenefitCategory.MEDICAL.toCode()));
		eob.getBenefitBalance().add(benefitBalances);

		BenefitComponent utilizationDayCount = new BenefitComponent(
				TransformerUtils.createCodeableConcept(TransformerConstants.CODING_BBAPI_BENEFIT_BALANCE_TYPE,
						TransformerConstants.CODED_BENEFIT_BALANCE_TYPE_SYSTEM_UTILIZATION_DAY_COUNT));
		utilizationDayCount.setUsed(new UnsignedIntType(claimGroup.getUtilizationDayCount().intValue()));
		benefitBalances.getFinancial().add(utilizationDayCount);

		if (claimGroup.getClaimHospiceStartDate().isPresent() || claimGroup.getBeneficiaryDischargeDate().isPresent()) {
			TransformerUtils.validatePeriodDates(claimGroup.getClaimHospiceStartDate(),
					claimGroup.getBeneficiaryDischargeDate());
			Period period = new Period();
			if (claimGroup.getClaimHospiceStartDate().isPresent()) {
				period.setStart(TransformerUtils.convertToDate(claimGroup.getClaimHospiceStartDate().get()),
						TemporalPrecisionEnum.DAY);
			}
			if (claimGroup.getBeneficiaryDischargeDate().isPresent()) {
				period.setEnd(TransformerUtils.convertToDate(claimGroup.getBeneficiaryDischargeDate().get()),
						TemporalPrecisionEnum.DAY);
			}
			eob.setHospitalization(period);
		}

		// Common group level fields between Inpatient, Outpatient Hospice, HHA and SNF
		TransformerUtils.mapEobCommonGroupInpOutHHAHospiceSNF(eob, claimGroup.getOrganizationNpi(),
				claimGroup.getClaimFacilityTypeCode(), claimGroup.getClaimFrequencyCode(),
				claimGroup.getClaimNonPaymentReasonCode(), claimGroup.getPatientDischargeStatusCode(),
				claimGroup.getClaimServiceClassificationTypeCode(), claimGroup.getClaimPrimaryPayerCode(),
				claimGroup.getAttendingPhysicianNpi(), claimGroup.getTotalChargeAmount(),
				claimGroup.getPrimaryPayerPaidAmount());

		for (Diagnosis diagnosis : TransformerUtils.extractDiagnoses1Thru12(claimGroup.getDiagnosisPrincipalCode(),
				claimGroup.getDiagnosisPrincipalCodeVersion(), claimGroup.getDiagnosis1Code(),
				claimGroup.getDiagnosis1CodeVersion(), claimGroup.getDiagnosis2Code(),
				claimGroup.getDiagnosis2CodeVersion(), claimGroup.getDiagnosis3Code(),
				claimGroup.getDiagnosis3CodeVersion(), claimGroup.getDiagnosis4Code(),
				claimGroup.getDiagnosis4CodeVersion(), claimGroup.getDiagnosis5Code(),
				claimGroup.getDiagnosis5CodeVersion(), claimGroup.getDiagnosis6Code(),
				claimGroup.getDiagnosis6CodeVersion(), claimGroup.getDiagnosis7Code(),
				claimGroup.getDiagnosis7CodeVersion(), claimGroup.getDiagnosis8Code(),
				claimGroup.getDiagnosis8CodeVersion(), claimGroup.getDiagnosis9Code(),
				claimGroup.getDiagnosis9CodeVersion(), claimGroup.getDiagnosis10Code(),
				claimGroup.getDiagnosis10CodeVersion(), claimGroup.getDiagnosis11Code(),
				claimGroup.getDiagnosis11CodeVersion(), claimGroup.getDiagnosis12Code(),
				claimGroup.getDiagnosis12CodeVersion()))
			TransformerUtils.addDiagnosisCode(eob, diagnosis);

		for (Diagnosis diagnosis : TransformerUtils.extractDiagnoses13Thru25(claimGroup.getDiagnosis13Code(),
				claimGroup.getDiagnosis13CodeVersion(), claimGroup.getDiagnosis14Code(),
				claimGroup.getDiagnosis14CodeVersion(), claimGroup.getDiagnosis15Code(),
				claimGroup.getDiagnosis15CodeVersion(), claimGroup.getDiagnosis16Code(),
				claimGroup.getDiagnosis16CodeVersion(), claimGroup.getDiagnosis17Code(),
				claimGroup.getDiagnosis17CodeVersion(), claimGroup.getDiagnosis18Code(),
				claimGroup.getDiagnosis18CodeVersion(), claimGroup.getDiagnosis19Code(),
				claimGroup.getDiagnosis19CodeVersion(), claimGroup.getDiagnosis20Code(),
				claimGroup.getDiagnosis20CodeVersion(), claimGroup.getDiagnosis21Code(),
				claimGroup.getDiagnosis21CodeVersion(), claimGroup.getDiagnosis22Code(),
				claimGroup.getDiagnosis22CodeVersion(), claimGroup.getDiagnosis23Code(),
				claimGroup.getDiagnosis23CodeVersion(), claimGroup.getDiagnosis24Code(),
				claimGroup.getDiagnosis24CodeVersion(), claimGroup.getDiagnosis25Code(),
				claimGroup.getDiagnosis25CodeVersion()))
			TransformerUtils.addDiagnosisCode(eob, diagnosis);

		for (Diagnosis diagnosis : TransformerUtils.extractExternalDiagnoses1Thru12(
				claimGroup.getDiagnosisExternalFirstCode(), claimGroup.getDiagnosisExternalFirstCodeVersion(),
				claimGroup.getDiagnosisExternal1Code(), claimGroup.getDiagnosisExternal1CodeVersion(),
				claimGroup.getDiagnosisExternal2Code(), claimGroup.getDiagnosisExternal2CodeVersion(),
				claimGroup.getDiagnosisExternal3Code(), claimGroup.getDiagnosisExternal3CodeVersion(),
				claimGroup.getDiagnosisExternal4Code(), claimGroup.getDiagnosisExternal4CodeVersion(),
				claimGroup.getDiagnosisExternal5Code(), claimGroup.getDiagnosisExternal5CodeVersion(),
				claimGroup.getDiagnosisExternal6Code(), claimGroup.getDiagnosisExternal6CodeVersion(),
				claimGroup.getDiagnosisExternal7Code(), claimGroup.getDiagnosisExternal7CodeVersion(),
				claimGroup.getDiagnosisExternal8Code(), claimGroup.getDiagnosisExternal8CodeVersion(),
				claimGroup.getDiagnosisExternal9Code(), claimGroup.getDiagnosisExternal9CodeVersion(),
				claimGroup.getDiagnosisExternal10Code(), claimGroup.getDiagnosisExternal10CodeVersion(),
				claimGroup.getDiagnosisExternal11Code(), claimGroup.getDiagnosisExternal11CodeVersion(),
				claimGroup.getDiagnosisExternal12Code(), claimGroup.getDiagnosisExternal12CodeVersion()))
			TransformerUtils.addDiagnosisCode(eob, diagnosis);

		for (HospiceClaimLine claimLine : claimGroup.getLines()) {
			ItemComponent item = eob.addItem();
			item.setSequence(claimLine.getLineNumber().intValue());

			if (claimLine.getHcpcsCode().isPresent()) {
				item.setService(
						TransformerUtils.createCodeableConcept(TransformerConstants.CODING_HCPCS,
								claimLine.getHcpcsCode().get()));
			}

			item.setLocation(new Address().setState((claimGroup.getProviderStateCode())));

			if (claimLine.getHcpcsInitialModifierCode().isPresent()) {
				item.addModifier(
						TransformerUtils.createCodeableConcept(TransformerConstants.CODING_HCPCS,
								claimLine.getHcpcsInitialModifierCode().get()));
			}
			if (claimLine.getHcpcsSecondModifierCode().isPresent()) {
				item.addModifier(
						TransformerUtils.createCodeableConcept(TransformerConstants.CODING_HCPCS,
								claimLine.getHcpcsSecondModifierCode().get()));
			}

			TransformerUtils.addExtensionCoding(item, TransformerConstants.CODING_FHIR_ACT_INVOICE_GROUP,
					TransformerConstants.CODING_FHIR_ACT_INVOICE_GROUP,
					TransformerConstants.CODED_ACT_INVOICE_GROUP_CLINICAL_SERVICES_AND_PRODUCTS);

			item.addAdjudication()
					.setCategory(
							TransformerUtils.createCodeableConcept(TransformerConstants.CODING_CCW_ADJUDICATION_CATEGORY,
							TransformerConstants.CODED_ADJUDICATION_PROVIDER_PAYMENT_AMOUNT))
					.getAmount().setSystem(TransformerConstants.CODING_MONEY)
					.setCode(TransformerConstants.CODED_MONEY_USD)
					.setValue(claimLine.getProviderPaymentAmount());

			item.addAdjudication()
					.setCategory(
							TransformerUtils.createCodeableConcept(TransformerConstants.CODING_CCW_ADJUDICATION_CATEGORY,
							TransformerConstants.CODED_ADJUDICATION_BENEFICIARY_PAYMENT_AMOUNT))
					.getAmount().setSystem(TransformerConstants.CODING_MONEY)
					.setCode(TransformerConstants.CODED_MONEY_USD)
					.setValue(claimLine.getBenficiaryPaymentAmount());

			item.addAdjudication()
					.setCategory(
							TransformerUtils.createCodeableConcept(TransformerConstants.CODING_CCW_ADJUDICATION_CATEGORY,
									TransformerConstants.CODED_ADJUDICATION_PAYMENT))
					.getAmount().setSystem(TransformerConstants.CODING_MONEY)
					.setCode(TransformerConstants.CODED_MONEY_USD)
					.setValue(claimLine.getPaymentAmount());

			// Common item level fields between Inpatient, Outpatient, HHA, Hospice and SNF
			TransformerUtils.mapEobCommonItemRevenue(item, eob, claimLine.getRevenueCenterCode(),
					claimLine.getRateAmount(),
					claimLine.getTotalChargeAmount(), claimLine.getNonCoveredChargeAmount().get(),
					claimLine.getUnitCount(), claimLine.getNationalDrugCodeQuantity(),
					claimLine.getNationalDrugCodeQualifierCode(), claimLine.getRevenueCenterRenderingPhysicianNPI());

			if (claimLine.getDeductibleCoinsuranceCd().isPresent()) {
				TransformerUtils.addExtensionCoding(item.getRevenue(),
						TransformerConstants.EXTENSION_CODING_CCW_DEDUCTIBLE_COINSURANCE_CODE,
						TransformerConstants.EXTENSION_CODING_CCW_DEDUCTIBLE_COINSURANCE_CODE,
						String.valueOf(claimLine.getDeductibleCoinsuranceCd().get()));
			}
		}
		return eob;
	}

}

