package gov.hhs.cms.bluebutton.server.app.stu3.providers;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.hl7.fhir.dstu3.model.ExplanationOfBenefit;
import org.hl7.fhir.dstu3.model.ExplanationOfBenefit.ItemComponent;
import org.hl7.fhir.exceptions.FHIRException;
import org.junit.Assert;
import org.junit.Test;

import gov.hhs.cms.bluebutton.data.model.rif.InpatientClaim;
import gov.hhs.cms.bluebutton.data.model.rif.InpatientClaimLine;
import gov.hhs.cms.bluebutton.data.model.rif.samples.StaticRifResource;
import gov.hhs.cms.bluebutton.data.model.rif.samples.StaticRifResourceGroup;
import gov.hhs.cms.bluebutton.server.app.ServerTestUtils;

/**
 * Unit tests for {@link InpatientClaimTransformer}.
 */
public final class InpatientClaimTransformerTest {
	/**
	 * Verifies that {@link InpatientClaimTransformer#transform(Object)} works
	 * as expected when run against the
	 * {@link StaticRifResource#SAMPLE_A_INPATIENT} {@link InpatientClaim}.
	 * 
	 * @throws FHIRException
	 *             (indicates test failure)
	 */
	@Test
	public void transformSampleARecord() throws FHIRException {
		List<Object> parsedRecords = ServerTestUtils
				.parseData(Arrays.asList(StaticRifResourceGroup.SAMPLE_A.getResources()));
		InpatientClaim claim = parsedRecords.stream().filter(r -> r instanceof InpatientClaim)
				.map(r -> (InpatientClaim) r).findFirst().get();

		ExplanationOfBenefit eob = InpatientClaimTransformer.transform(claim);
		assertMatches(claim, eob);
	}

	/**
	 * Verifies that the {@link ExplanationOfBenefit} "looks like" it should, if
	 * it were produced from the specified {@link InpatientClaim}.
	 * 
	 * @param claim
	 *            the {@link InpatientClaim} that the {@link ExplanationOfBenefit} was
	 *            generated from
	 * @param eob
	 *            the {@link ExplanationOfBenefit} that was generated from the
	 *            specified {@link InpatientClaim}
	 * @throws FHIRException
	 *             (indicates test failure)
	 */
	static void assertMatches(InpatientClaim claim, ExplanationOfBenefit eob) throws FHIRException {
		TransformerTestUtils.assertNoEncodedOptionals(eob);

		Assert.assertEquals(TransformerUtils.buildEobId(ClaimType.INPATIENT, claim.getClaimId()),
				eob.getIdElement().getIdPart());

		TransformerTestUtils.assertIdentifierExists(TransformerConstants.CODING_CCW_CLAIM_ID, claim.getClaimId(),
				eob.getIdentifier());
		TransformerTestUtils.assertIdentifierExists(TransformerConstants.CODING_CCW_CLAIM_GROUP_ID,
				claim.getClaimGroupId().toPlainString(), eob.getIdentifier());
		Assert.assertEquals(TransformerUtils.referencePatient(claim.getBeneficiaryId()).getReference(),
				eob.getPatient().getReference());
		Assert.assertEquals(
				TransformerUtils.referenceCoverage(claim.getBeneficiaryId(), MedicareSegment.PART_A).getReference(),
				eob.getInsurance().getCoverage().getReference());
		Assert.assertEquals("active", eob.getStatus().toCode());

		TransformerTestUtils.assertDateEquals(claim.getDateFrom(), eob.getBillablePeriod().getStartElement());
		TransformerTestUtils.assertDateEquals(claim.getDateThrough(), eob.getBillablePeriod().getEndElement());

		// test the common field provider number is set as expected in the EOB
		TransformerTestUtils.assertProviderNumber(eob, claim.getProviderNumber());
		
		Assert.assertEquals(claim.getPaymentAmount(), eob.getPayment().getAmount().getValue());

		TransformerTestUtils.assertDateEquals(claim.getClaimAdmissionDate().get(), eob.getHospitalization().getStartElement());
		TransformerTestUtils.assertDateEquals(claim.getBeneficiaryDischargeDate().get(), eob.getHospitalization().getEndElement());

		TransformerTestUtils.assertBenefitBalanceEquals(TransformerConstants.CODING_BBAPI_BENEFIT_BALANCE_TYPE,
				TransformerConstants.CODED_BENEFIT_BALANCE_TYPE_PASS_THRU_PER_DIEM, claim.getPassThruPerDiemAmount(),
				eob.getBenefitBalanceFirstRep().getFinancial());
		TransformerTestUtils.assertBenefitBalanceEquals(TransformerConstants.CODING_BBAPI_BENEFIT_BALANCE_TYPE,
				TransformerConstants.CODED_BENEFIT_BALANCE_TYPE_PROFFESIONAL_COMPONENT_CHARGE, claim.getProfessionalComponentCharge(),
				eob.getBenefitBalanceFirstRep().getFinancial());
		TransformerTestUtils.assertBenefitBalanceEquals(TransformerConstants.CODING_BBAPI_BENEFIT_BALANCE_TYPE,
				TransformerConstants.CODED_BENEFIT_BALANCE_TYPE_TOTAL_PPS_CAPITAL, claim.getClaimTotalPPSCapitalAmount().get(),
				eob.getBenefitBalanceFirstRep().getFinancial());
		TransformerTestUtils.assertBenefitBalanceUsedEquals(TransformerConstants.CODING_BBAPI_BENEFIT_BALANCE_TYPE,
				TransformerConstants.CODED_BENEFIT_BALANCE_TYPE_SYSTEM_UTILIZATION_DAY_COUNT, claim.getUtilizationDayCount().intValue(),
				eob.getBenefitBalanceFirstRep().getFinancial());
		
		// test common benefit components between SNF and Inpatient claims are set as expected
		TransformerTestUtils.assertCommonBenefitComponentInpatientSNF(eob, claim.getCoinsuranceDayCount(),
				claim.getNonUtilizationDayCount(), claim.getDeductibleAmount(),
				claim.getPartACoinsuranceLiabilityAmount(), claim.getBloodPintsFurnishedQty(),
				claim.getNoncoveredCharge(), claim.getTotalDeductionAmount(),
				claim.getClaimPPSCapitalDisproportionateShareAmt(), claim.getClaimPPSCapitalExceptionAmount(),
				claim.getClaimPPSCapitalFSPAmount(), claim.getClaimPPSCapitalIMEAmount(),
				claim.getClaimPPSCapitalOutlierAmount(), claim.getClaimPPSOldCapitalHoldHarmlessAmount());
		
		// test common eob information between SNF and Inpatient claims are set as expected
		TransformerTestUtils.assertCommonEobInformationInpatientSNF(eob, claim.getNoncoveredStayFromDate(),
				claim.getNoncoveredStayThroughDate(), claim.getCoveredCareThoughDate(),
				claim.getMedicareBenefitsExhaustedDate(), claim.getDiagnosisRelatedGroupCd());

		TransformerTestUtils.assertBenefitBalanceEquals(TransformerConstants.CODING_BBAPI_BENEFIT_BALANCE_TYPE,
				TransformerConstants.CODED_BENEFIT_BALANCE_TYPE_DRUG_OUTLIER_APPROVED_PAYMENT,
				claim.getDrgOutlierApprovedPaymentAmount().get(), eob.getBenefitBalanceFirstRep().getFinancial());

		// Test to ensure common group fields between Inpatient, Outpatient and SNF
		// match
		TransformerTestUtils.assertEobCommonGroupInpOutSNFEquals(eob, claim.getBloodDeductibleLiabilityAmount(),
				claim.getOperatingPhysicianNpi(), claim.getOtherPhysicianNpi(), claim.getClaimQueryCode(),
				claim.getMcoPaidSw());

		// Test to ensure common group fields between Inpatient, Outpatient HHA, Hospice
		// and SNF match
		TransformerTestUtils.assertEobCommonGroupInpOutHHAHospiceSNFEquals(eob, claim.getOrganizationNpi(),
				claim.getClaimFacilityTypeCode(), claim.getClaimFrequencyCode(), claim.getClaimNonPaymentReasonCode(),
				claim.getPatientDischargeStatusCode(), claim.getClaimServiceClassificationTypeCode(),
				claim.getClaimPrimaryPayerCode(), claim.getAttendingPhysicianNpi(), claim.getTotalChargeAmount(),
				claim.getPrimaryPayerPaidAmount());

		Assert.assertEquals(9, eob.getDiagnosis().size());

		CCWProcedure ccwProcedure = new CCWProcedure(claim.getProcedure1Code(), claim.getProcedure1CodeVersion(),
				claim.getProcedure1Date());
		TransformerTestUtils.assertHasCoding(ccwProcedure.getFhirSystem().toString(),
				claim.getProcedure1Code().get(),
				eob.getProcedure().get(0).getProcedureCodeableConcept());
		Assert.assertEquals(
				Date.from(claim.getProcedure1Date().get().atStartOfDay(ZoneId.systemDefault()).toInstant()),
				eob.getProcedure().get(0).getDate());

		Assert.assertEquals(1, eob.getItem().size());
		ItemComponent eobItem0 = eob.getItem().get(0);
		InpatientClaimLine claimLine1 = claim.getLines().get(0);
		Assert.assertEquals(claimLine1.getLineNumber(), new BigDecimal(eobItem0.getSequence()));

		TransformerTestUtils.assertExtensionCodingEquals(eobItem0, TransformerConstants.CODING_FHIR_ACT_INVOICE_GROUP,
				TransformerConstants.CODING_FHIR_ACT_INVOICE_GROUP,
				(TransformerConstants.CODED_ACT_INVOICE_GROUP_CLINICAL_SERVICES_AND_PRODUCTS));

		Assert.assertEquals(claim.getProviderStateCode(), eobItem0.getLocationAddress().getState());

		TransformerTestUtils.assertHasCoding(TransformerConstants.CODING_HCPCS, claimLine1.getHcpcsCode().get(),
				eobItem0.getService());

		TransformerTestUtils.assertExtensionCodingEquals(eobItem0.getRevenue(), TransformerConstants.EXTENSION_CODING_CCW_DEDUCTIBLE_COINSURANCE_CODE,
				TransformerConstants.EXTENSION_CODING_CCW_DEDUCTIBLE_COINSURANCE_CODE,
				String.valueOf(claimLine1.getDeductibleCoinsuranceCd().get()));

		// Test to ensure item level fields between Inpatient, Outpatient, HHA, Hopsice
		// and SNF match
		TransformerTestUtils.assertEobCommonItemRevenueEquals(eobItem0, eob, claimLine1.getRevenueCenter(),
				claimLine1.getRateAmount(), claimLine1.getTotalChargeAmount(), claimLine1.getNonCoveredChargeAmount(),
				claimLine1.getUnitCount(), claimLine1.getNationalDrugCodeQuantity(),
				claimLine1.getNationalDrugCodeQualifierCode(), claimLine1.getRevenueCenterRenderingPhysicianNPI());
		
		// verify {@link
		// TransformerUtils#mapEobType(CodeableConcept,ClaimType,Optional,Optional)}
		// method worked as expected for this claim type
		TransformerTestUtils.assertMapEobType(eob.getType(), ClaimType.INPATIENT,
				Optional.of(org.hl7.fhir.dstu3.model.codesystems.ClaimType.INSTITUTIONAL),
				Optional.of(claim.getNearLineRecordIdCode()), Optional.of(claim.getClaimTypeCode()));
	}
		
}
