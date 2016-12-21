package gov.hhs.cms.bluebutton.datapipeline.rif.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * Models rows from {@link RifFileType#OUTPATIENT} RIF files. Rows in this file
 * are grouped, such that there is one group per claim, with multiple rows: one
 * for each claim line.
 * </p>
 * <p>
 * The RIF file layout used here is specific to the Blue Button API's ETL
 * process. The layouts of these files is detailed in the
 * <code>bluebutton-data-pipeline-rif/dev/rif-record-layout.xlsx</code> file.
 * The columns contained in the files are largely similar to those detailed in
 * <a href="https://www.ccwdata.org/web/guest/data-dictionaries">CCW: Data
 * Dictionaries</a>.
 * </p>
 * <p>
 * Design Note: This class is too painful to maintain as a bean, so I've
 * stripped it down to just a struct. To be clear, this is <strong>not</strong>
 * immutable and thus <strong>not</strong> thread-safe (and it really shouldn't
 * need to be).
 * </p>
 */
public final class OutpatientClaimGroup {


	/**
	 * @see Column#VERSION
	 */
	public int version;

	/**
	 * @see Column#DML_IND
	 */
	public RecordAction recordAction;

	/**
	 * @see Column#BENE_ID
	 */
	public String beneficiaryId;

	/**
	 * @see Column#CLM_ID
	 */
	public String claimId;

	/**
	 * @see Column#NCH_NEAR_LINE_REC_IDENT_CD
	 */
	public Character nearLineRecordIdCode;

	/**
	 * @see Column#NCH_CLM_TYPE_CD
	 */
	public String claimTypeCode;

	/**
	 * @see Column#CLM_FROM_DT
	 */
	public LocalDate dateFrom;

	/**
	 * @see Column#CLM_THRU_DT
	 */
	public LocalDate dateThrough;

	/**
	 * @see Column#NCH_WKLY_PROC_DT
	 */
	public LocalDate weeklyProcessDate;

	/**
	 * @see Column#PRVDR_NUM
	 */
	public String providerNumber;

	/**
	 * @see Column#CLM_FAC_TYPE_CD
	 */
	public Character claimFacilityTypeCode;

	/**
	 * @see Column#CLM_SRVC_CLSFCTN_TYPE_CD
	 */
	public Character claimServiceClassificationTypeCode;

	/**
	 * @see Column#CLM_FREQ_CD
	 */
	public Character claimFrequencyCode;

	/**
	 * @see Column#CLM_MDCR_NON_PMT_RSN_CD
	 */
	public Optional<String> claimNonPaymentReasonCode;

	/**
	 * @see Column#CLM_PMT_AMT
	 */
	public BigDecimal paymentAmount;

	/**
	 * @see Column#NCH_PRMRY_PYR_CLM_PD_AMT
	 */
	public BigDecimal primaryPayerPaidAmount;

	/**
	 * @see Column#NCH_PRMRY_PYR_CD
	 */
	public Character claimPrimaryPayerCode;

	/**
	 * @see Column#PRVDR_STATE_CD
	 */
	public String providerStateCode;

	/**
	 * @see Column#ORG_NPI_NUM
	 */
	public Optional<String> organizationNpi;

	/**
	 * @see Column#AT_PHYSN_NPI
	 */
	public Optional<String> attendingPhysicianNpi;

	/**
	 * @see Column#OP_PHYSN_NPI
	 */
	public Optional<String> operatingPhysicianNpi;

	/**
	 * @see Column#OT_PHYSN_NPI
	 */
	public Optional<String> otherPhysicianNpi;

	/**
	 * @see Column#CLM_MCO_PD_SW
	 */
	public Optional<Character> mcoPaidSw;

	/**
	 * @see Column#PTNT_DSCHRG_STUS_CD
	 */
	public Optional<String> patientDischargeStatusCode;

	/**
	 * @see Column#CLM_TOT_CHRG_AMT
	 */
	public BigDecimal totalChargeAmount;

	/**
	 * @see Column#NCH_BENE_BLOOD_DDCTBL_LBLTY_AM
	 */
	public BigDecimal bloodDeductibleLiabilityAmount;

	/**
	 * @see Column#NCH_PROFNL_CMPNT_CHRG_AMT
	 */
	public BigDecimal professionalComponentCharge;

	/**
	 * @see Column#PRNCPAL_DGNS_CD
	 * @see Column#PRNCPAL_DGNS_VRSN_CD
	 */
	public IcdCode diagnosisPrincipal;

	/**
	 * See {@link Column#ICD_DGNS_CD1} through {@link Column#ICD_DGNS_CD25} and
	 * {@link Column#ICD_DGNS_VRSN_CD1} through
	 * {@link Column#ICD_DGNS_VRSN_CD25}.
	 */
	public List<IcdCode> diagnosesAdditional = new LinkedList<>();

	/**
	 * @see Column#FST_DGNS_E_CD
	 * @see Column#FST_DGNS_E_VRSN_CD
	 */
	public Optional<IcdCode> diagnosisFirstClaimExternal;

	/**
	 * See {@link Column#ICD_DGNS_E_CD1} through {@link Column#ICD_DGNS_E_CD12}
	 * and {@link Column#ICD_DGNS_E_VRSN_CD1} through
	 * {@link Column#ICD_DGNS_E_VRSN_CD12}.
	 */
	public List<IcdCode> diagnosesExternal = new LinkedList<>();

	/**
	 * See {@link Column#ICD_PRCDR_CD1} through {@link Column#ICD_PRCDR_CD25}
	 * and {@link Column#ICD_PRCDR_VRSN_CD1} through
	 * {@link Column#ICD_PRCDR_VRSN_CD25} and {@link Column#PRCDR_DT1} through
	 * {@link Column#PRCDR_DT25}.
	 */
	public List<IcdCode> procedureCodes = new LinkedList<>();

	/**
	 * See {@link Column#RSN_VISIT_CD1} through {@link Column#RSN_VISIT_CD3} and
	 * {@link Column#RSN_VISIT_VRSN_CD1} through
	 * {@link Column#RSN_VISIT_VRSN_CD3}.
	 */
	public List<IcdCode> diagnosesReasonForVisit = new LinkedList<>();

	/**
	 * @see Column#NCH_BENE_PTB_DDCTBL_AMT
	 */
	public BigDecimal deductibleAmount;

	/**
	 * @see Column#NCH_BENE_PTB_COINSRNC_AMT
	 */
	public BigDecimal coninsuranceAmount;

	/**
	 * @see Column#CLM_OP_PRVDR_PMT_AMT
	 */
	public BigDecimal providerPaymentAmount;

	/**
	 * @see Column#CLM_OP_BENE_PMT_AMT
	 */
	public BigDecimal beneficiaryPaymentAmount;

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("OutpatientClaimGroup [version=");
		builder.append(version);
		builder.append(", recordAction=");
		builder.append(recordAction);
		builder.append(", beneficiaryId=");
		builder.append(beneficiaryId);
		builder.append(", claimId=");
		builder.append(claimId);
		builder.append(", nearLineRecordIdCode=");
		builder.append(nearLineRecordIdCode);
		builder.append(", claimTypeCode=");
		builder.append(claimTypeCode);
		builder.append(", dateFrom=");
		builder.append(dateFrom);
		builder.append(", dateThrough=");
		builder.append(dateThrough);
		builder.append(", weeklyProcessDate=");
		builder.append(weeklyProcessDate);
		builder.append(", providerNumber=");
		builder.append(providerNumber);
		builder.append(", claimFacilityTypeCode=");
		builder.append(claimFacilityTypeCode);
		builder.append(", claimServiceClassificationTypeCode=");
		builder.append(claimServiceClassificationTypeCode);
		builder.append(", claimNonPaymentReasonCode=");
		builder.append(claimNonPaymentReasonCode);
		builder.append(", paymentAmount=");
		builder.append(paymentAmount);
		builder.append(", primaryPayerPaidAmount=");
		builder.append(primaryPayerPaidAmount);
		builder.append(", providerStateCode=");
		builder.append(providerStateCode);
		builder.append(", organizationNpi=");
		builder.append(organizationNpi);
		builder.append(", attendingPhysicianNpi=");
		builder.append(attendingPhysicianNpi);
		builder.append(", operatingPhysicianNpi=");
		builder.append(operatingPhysicianNpi);
		builder.append(", otherPhysicianNpi=");
		builder.append(otherPhysicianNpi);
		builder.append(", patientDischargeStatusCode=");
		builder.append(patientDischargeStatusCode);
		builder.append(", totalChargeAmount=");
		builder.append(totalChargeAmount);
		builder.append(", bloodDeductibleLiabilityAmount=");
		builder.append(bloodDeductibleLiabilityAmount);
		builder.append(", professionalComponentCharge=");
		builder.append(professionalComponentCharge);
		builder.append(", diagnosisPrincipal=");
		builder.append(diagnosisPrincipal);
		builder.append(", diagnosesAdditional=");
		builder.append(diagnosesAdditional);
		builder.append(", diagnosisFirstClaimExternal=");
		builder.append(diagnosisFirstClaimExternal);
		builder.append(", diagnosesExternal=");
		builder.append(diagnosesExternal);
		builder.append(", procedureCodes=");
		builder.append(procedureCodes);
		builder.append(", diagnosesReasonForVisit=");
		builder.append(diagnosesReasonForVisit);
		builder.append(", deductibleAmount=");
		builder.append(deductibleAmount);
		builder.append(", coninsuranceAmount=");
		builder.append(coninsuranceAmount);
		builder.append(", providerPaymentAmount=");
		builder.append(providerPaymentAmount);
		builder.append(", beneficiaryPaymentAmount=");
		builder.append(beneficiaryPaymentAmount);
		builder.append(", lines=");
		builder.append(lines);
		builder.append("]");
		return builder.toString();
	}

	/**
	 * Represents the data contained in {@link Column#CLM_LINE_NUM} and
	 * subsequent columns: one entry for every "claim line" in the claim
	 * represented by this {@link OutpatientClaimGroup} instance.
	 */
	public List<OutpatientClaimLine> lines = new LinkedList<>();

	/**
	 * Models individual claim lines within a {@link OutpatientClaimGroup}
	 * instance.
	 */
	public static final class OutpatientClaimLine {

		/**
		 * @see Column#CLM_LINE_NUM
		 */
		public Integer lineNumber;

		/**
		 * @see Column#HCPCS_CD
		 */
		public Optional<String> hcpcsCode;

		/**
		 * @see Column#HCPCS_1ST_MDFR_CD
		 */
		public Optional<String> hcpcsInitialModifierCode;

		/**
		 * @see Column#HCPCS_2ND_MDFR_CD
		 */
		public Optional<String> hcpcsSecondModifierCode;

		/**
		 * @see Column#REV_CNTR_IDE_NDC_UPC_NUM
		 */
		public Optional<String> nationalDrugCode;

		/**
		 * @see Column#REV_CNTR_BLOOD_DDCTBL_AMT
		 */
		public BigDecimal bloodDeductibleAmount;

		/**
		 * @see Column#REV_CNTR_CASH_DDCTBL_AMT
		 */
		public BigDecimal cashDeductibleAmount;

		/**
		 * @see Column#REV_CNTR_COINSRNC_WGE_ADJSTD_C
		 * 
		 */
		public BigDecimal wageAdjustedCoinsuranceAmount;

		/**
		 * @see Column#REV_CNTR_RDCD_COINSRNC_AMT
		 */
		public BigDecimal reducedCoinsuranceAmount;

		/**
		 * @see Column#REV_CNTR_PRVDR_PMT_AMT
		 */
		public BigDecimal providerPaymentAmount;

		/**
		 * @see Column#REV_CNTR_BENE_PMT_AMT
		 */
		public BigDecimal benficiaryPaymentAmount;

		/**
		 * @see Column#REV_CNTR_PTNT_RSPNSBLTY_PMT
		 */
		public BigDecimal patientResponsibilityAmount;

		/**
		 * @see Column#REV_CNTR_PMT_AMT_AMT
		 */
		public BigDecimal paymentAmount;

		/**
		 * @see Column#REV_CNTR_TOT_CHRG_AMT
		 */
		public BigDecimal totalChargeAmount;

		/**
		 * @see Column#REV_CNTR_NCVRD_CHRG_AMT
		 */
		public BigDecimal nonCoveredChargeAmount;
		
		/**
		 * @see Column#REV_CNTR_NDC_QTY
		 */
		public Optional<Integer> nationalDrugCodeQuantity;

		/**
		 * @see Column#REV_CNTR_NDC_QTY_QLFR_CD
		 */
		public Optional<String> nationalDrugCodeQualifierCode;

		/**
		 * @see Column#RNDRNG_PHYSN_NPI
		 */
		public Optional<String> revenueCenterRenderingPhysicianNPI;

		/**
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("OutpatientClaimLine [lineNumber=");
			builder.append(lineNumber);
			builder.append(", hcpcsCode=");
			builder.append(hcpcsCode);
			builder.append(", hcpcsInitialModifierCode=");
			builder.append(hcpcsInitialModifierCode);
			builder.append(", hcpcsSecondModifierCode=");
			builder.append(hcpcsSecondModifierCode);
			builder.append(", nationalDrugCode=");
			builder.append(nationalDrugCode);
			builder.append(", bloodDeductibleAmount=");
			builder.append(bloodDeductibleAmount);
			builder.append(", cashDeductibleAmount=");
			builder.append(cashDeductibleAmount);
			builder.append(", wageAdjustedCoinsuranceAmount=");
			builder.append(wageAdjustedCoinsuranceAmount);
			builder.append(", reducedCoinsuranceAmount=");
			builder.append(reducedCoinsuranceAmount);
			builder.append(", providerPaymentAmount=");
			builder.append(providerPaymentAmount);
			builder.append(", benficiaryPaymentAmount=");
			builder.append(benficiaryPaymentAmount);
			builder.append(", patientResponsibilityAmount=");
			builder.append(patientResponsibilityAmount);
			builder.append(", paymentAmount=");
			builder.append(paymentAmount);
			builder.append(", totalChargeAmount=");
			builder.append(totalChargeAmount);
			builder.append(", nonCoveredChargeAmount=");
			builder.append(nonCoveredChargeAmount);
			builder.append(", nationalDrugCodeQuantity=");
			builder.append(nationalDrugCodeQuantity);
			builder.append(", nationalDrugCodeQualifierCode=");
			builder.append(nationalDrugCodeQualifierCode);
			builder.append(", revenueCenterRenderingPhysicianNPI=");
			builder.append(revenueCenterRenderingPhysicianNPI);
			builder.append("]");
			return builder.toString();
		}
	}

	/**
	 * Enumerates the raw RIF columns represented in
	 * {@link OutpatientClaimGroup}, where {@link Column#ordinal()} values
	 * represent each column's position in the actual data.
	 */
	public static enum Column {
		/**
		 * Type: (unknown), max chars: (unknown).
		 */
		VERSION,

		/**
		 * Type: (unknown), max chars: (unknown).
		 */
		DML_IND,

		/**
		 * Type: <code>CHAR</code>, max chars: 15. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/bene_id.txt">
		 * CCW Data Dictionary: BENE_ID</a>, though note that this instance of
		 * the field is unencrypted.
		 */
		BENE_ID,

		/**
		 * Type: <code>CHAR</code>, max chars: 15. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/clm_id.txt">
		 * CCW Data Dictionary: CLM_ID</a>.
		 */
		CLM_ID,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/ric_cd.txt">
		 * CCW Data Dictionary: RIC_CD</a>.
		 */
		NCH_NEAR_LINE_REC_IDENT_CD,

		/**
		 * Type: <code>CHAR</code>, max chars: 2. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/clm_type.txt">
		 * CCW Data Dictionary: CLM_TYPE</a>.
		 */
		NCH_CLM_TYPE_CD,

		/**
		 * Type: <code>DATE</code>, max chars: 8. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/from_dt.txt">
		 * CCW Data Dictionary: FROM_DT</a>.
		 */
		CLM_FROM_DT,

		/**
		 * Type: <code>DATE</code>, max chars: 8. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/thru_dt.txt">
		 * CCW Data Dictionary: THRU_DT</a>.
		 */
		CLM_THRU_DT,

		/**
		 * Type: <code>DATE</code>, max chars: 8. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/wkly_dt.txt">
		 * CCW Data Dictionary: WKLY_DT</a>.
		 */
		NCH_WKLY_PROC_DT,

		/**
		 * NOT MAPPED
		 */
		FI_CLM_PROC_DT,

		/**
		 * NOT MAPPED
		 */
		CLAIM_QUERY_CODE,

		/**
		 * Type: <code>CHAR</code>, max chars: 6. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/provider.txt">
		 * CCW Data Dictionary: PRVDR_NUM</a>.
		 */
		PRVDR_NUM,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/fac_type.txt">
		 * CCW Data Dictionary: FAC_TYPE</a>.
		 */
		CLM_FAC_TYPE_CD,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/typesrvc.txt">
		 * CCW Data Dictionary: TYPESRVC</a>.
		 */
		CLM_SRVC_CLSFCTN_TYPE_CD,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/freq_cd.txt">
		 * CCW Data Dictionary: FREQ_CD</a>.
		 */
		CLM_FREQ_CD,

		/**
		 * NOT MAPPED
		 */
		FI_NUM,

		/**
		 * Type: <code>CHAR</code>, max chars: 2 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/nopay_cd.txt">
		 * CCW Data Dictionary: NOPAY_CD</a>.
		 */
		CLM_MDCR_NON_PMT_RSN_CD,

		/**
		 * Type: <code>NUM</code>, max chars: 12. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/pmt_amt.txt">
		 * CCW Data Dictionary: PMT_AMT</a>.
		 */
		CLM_PMT_AMT,

		/**
		 * Type: <code>NUM</code>, max chars: 12. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/prpayamt.txt">
		 * CCW Data Dictionary: PRPAYAMT</a>.
		 */
		NCH_PRMRY_PYR_CLM_PD_AMT,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/prpay_cd.txt">
		 * CCW Data Dictionary: PRPAY_CD</a>.
		 */
		NCH_PRMRY_PYR_CD,

		/**
		 * Type: <code>CHAR</code>, max chars: 2. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/prstate.txt">
		 * CCW Data Dictionary: PRSTATE</a>.
		 */
		PRVDR_STATE_CD,

		/**
		 * Type: <code>CHAR</code>, max chars: 10 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/orgnpinm.txt">
		 * CCW Data Dictionary: ORGNPINM</a>.
		 */
		ORG_NPI_NUM,

		/**
		 * NOT MAPPED
		 */
		AT_PHYSN_UPIN,

		/**
		 * Type: <code>CHAR</code>, max chars: 12 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/at_npi.txt">
		 * CCW Data Dictionary: AT_NPI</a>.
		 */
		AT_PHYSN_NPI,

		/**
		 * NOT MAPPED
		 */
		OP_PHYSN_UPIN,

		/**
		 * Type: <code>CHAR</code>, max chars: 12 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/op_npi.txt">
		 * CCW Data Dictionary: OP_NPI</a>.
		 */
		OP_PHYSN_NPI,

		/**
		 * NOT MAPPED
		 */
		OT_PHYSN_UPIN,

		/**
		 * Type: <code>CHAR</code>, max chars: 12 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/ot_npi.txt">
		 * CCW Data Dictionary: OT_NPI</a>.
		 */
		OT_PHYSN_NPI,

		/**
		 * Type: <code>CHAR</code>, max chars: 1 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/mcopdsw.txt">
		 * CCW Data Dictionary: MCOPDSW</a>.
		 */
		CLM_MCO_PD_SW,

		/**
		 * Type: <code>CHAR</code>, max chars: 2 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/stus_cd.txt">
		 * CCW Data Dictionary: STUS_CD</a>.
		 */
		PTNT_DSCHRG_STUS_CD,

		/**
		 * Type: <code>NUM</code>, max chars: 12. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/tot_chrg.txt">
		 * CCW Data Dictionary: TOT_CHRG</a>.
		 */
		CLM_TOT_CHRG_AMT,

		/**
		 * Type: <code>NUM</code>, max chars: 12. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/blddedam.txt">
		 * CCW Data Dictionary: BLDDEDAM</a>.
		 */
		NCH_BENE_BLOOD_DDCTBL_LBLTY_AM,

		/**
		 * Type: <code>NUM</code>, max chars: 12. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/pcchgamt.txt">
		 * CCW Data Dictionary: PCCHGAMT </a>.
		 */
		NCH_PROFNL_CMPNT_CHRG_AMT,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/prncpal_dgns_cd.txt">
		 * CCW Data Dictionary: PRNCPAL_DGNS_CD</a>.
		 */
		PRNCPAL_DGNS_CD,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/prncpal_dgns_vrsn_cd.txt">
		 * CCW Data Dictionary: PRNCPAL_DGNS_VRSN_CD</a>.
		 */
		PRNCPAL_DGNS_VRSN_CD,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_cd1.txt">
		 * CCW Data Dictionary: ICD_DGNS_CD1</a>.
		 */
		ICD_DGNS_CD1,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_vrsn_cd1.txt">
		 * CCW Data Dictionary: ICD_DGNS_VRSN_CD1</a>.
		 */
		ICD_DGNS_VRSN_CD1,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_cd2.txt">
		 * CCW Data Dictionary: ICD_DGNS_CD2</a>.
		 */
		ICD_DGNS_CD2,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_vrsn_cd2.txt">
		 * CCW Data Dictionary: ICD_DGNS_VRSN_CD2</a>.
		 */
		ICD_DGNS_VRSN_CD2,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_cd3.txt">
		 * CCW Data Dictionary: ICD_DGNS_CD3</a>.
		 */
		ICD_DGNS_CD3,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_vrsn_cd3.txt">
		 * CCW Data Dictionary: ICD_DGNS_VRSN_CD3</a>.
		 */
		ICD_DGNS_VRSN_CD3,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_cd4.txt">
		 * CCW Data Dictionary: ICD_DGNS_CD4</a>.
		 */
		ICD_DGNS_CD4,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_vrsn_cd4.txt">
		 * CCW Data Dictionary: ICD_DGNS_VRSN_CD4</a>.
		 */
		ICD_DGNS_VRSN_CD4,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_cd5.txt">
		 * CCW Data Dictionary: ICD_DGNS_CD5</a>.
		 */
		ICD_DGNS_CD5,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_vrsn_cd5.txt">
		 * CCW Data Dictionary: ICD_DGNS_VRSN_CD5</a>.
		 */
		ICD_DGNS_VRSN_CD5,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_cd6.txt">
		 * CCW Data Dictionary: ICD_DGNS_CD6</a>.
		 */
		ICD_DGNS_CD6,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_vrsn_cd6.txt">
		 * CCW Data Dictionary: ICD_DGNS_VRSN_CD6</a>.
		 */
		ICD_DGNS_VRSN_CD6,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_cd7.txt">
		 * CCW Data Dictionary: ICD_DGNS_CD7</a>.
		 */
		ICD_DGNS_CD7,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_vrsn_cd7.txt">
		 * CCW Data Dictionary: ICD_DGNS_VRSN_CD7</a>.
		 */
		ICD_DGNS_VRSN_CD7,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_cd8.txt">
		 * CCW Data Dictionary: ICD_DGNS_CD8</a>.
		 */
		ICD_DGNS_CD8,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_vrsn_cd8.txt">
		 * CCW Data Dictionary: ICD_DGNS_VRSN_CD8</a>.
		 */
		ICD_DGNS_VRSN_CD8,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_cd9.txt">
		 * CCW Data Dictionary: ICD_DGNS_CD9</a>.
		 */
		ICD_DGNS_CD9,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_vrsn_cd9.txt">
		 * CCW Data Dictionary: ICD_DGNS_VRSN_CD9</a>.
		 */
		ICD_DGNS_VRSN_CD9,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_cd10.txt">
		 * CCW Data Dictionary: ICD_DGNS_CD10</a>.
		 */
		ICD_DGNS_CD10,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_vrsn_cd10.txt">
		 * CCW Data Dictionary: ICD_DGNS_VRSN_CD10</a>.
		 */
		ICD_DGNS_VRSN_CD10,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_cd11.txt">
		 * CCW Data Dictionary: ICD_DGNS_CD11</a>.
		 */
		ICD_DGNS_CD11,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_vrsn_cd11.txt">
		 * CCW Data Dictionary: ICD_DGNS_VRSN_CD11</a>.
		 */
		ICD_DGNS_VRSN_CD11,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_cd12.txt">
		 * CCW Data Dictionary: ICD_DGNS_CD12</a>.
		 */
		ICD_DGNS_CD12,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_vrsn_cd12.txt">
		 * CCW Data Dictionary: ICD_DGNS_VRSN_CD12</a>.
		 */
		ICD_DGNS_VRSN_CD12,

		/**
		 * Type: <code>CHAR</code>, max chars: 19. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_cd13.txt">
		 * CCW Data Dictionary: ICD_DGNS_CD13</a>.
		 */
		ICD_DGNS_CD13,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_vrsn_cd13.txt">
		 * CCW Data Dictionary: ICD_DGNS_VRSN_CD13</a>.
		 */
		ICD_DGNS_VRSN_CD13,

		/**
		 * Type: <code>CHAR</code>, max chars: 19. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_cd14.txt">
		 * CCW Data Dictionary: ICD_DGNS_CD14</a>.
		 */
		ICD_DGNS_CD14,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_vrsn_cd14.txt">
		 * CCW Data Dictionary: ICD_DGNS_VRSN_CD14</a>.
		 */
		ICD_DGNS_VRSN_CD14,

		/**
		 * Type: <code>CHAR</code>, max chars: 19. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_c15.txt">
		 * CCW Data Dictionary: ICD_DGNS_C15</a>.
		 */
		ICD_DGNS_C15,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_vrsn_c15.txt">
		 * CCW Data Dictionary: ICD_DGNS_VRSN_C15</a>.
		 */
		ICD_DGNS_VRSN_C15,

		/**
		 * Type: <code>CHAR</code>, max chars: 19. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_c16.txt">
		 * CCW Data Dictionary: ICD_DGNS_C16</a>.
		 */
		ICD_DGNS_C16,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_vrsn_c16.txt">
		 * CCW Data Dictionary: ICD_DGNS_VRSN_C16</a>.
		 */
		ICD_DGNS_VRSN_C16,

		/**
		 * Type: <code>CHAR</code>, max chars: 19. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_cd17.txt">
		 * CCW Data Dictionary: ICD_DGNS_CD17</a>.
		 */
		ICD_DGNS_CD17,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_vrsn_cd17.txt">
		 * CCW Data Dictionary: ICD_DGNS_VRSN_CD17</a>.
		 */
		ICD_DGNS_VRSN_CD17,

		/**
		 * Type: <code>CHAR</code>, max chars: 19. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_cd18.txt">
		 * CCW Data Dictionary: ICD_DGNS_CD18</a>.
		 */
		ICD_DGNS_CD18,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_vrsn_cd18.txt">
		 * CCW Data Dictionary: ICD_DGNS_VRSN_CD18</a>.
		 */
		ICD_DGNS_VRSN_CD18,

		/**
		 * Type: <code>CHAR</code>, max chars: 19. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_cd19.txt">
		 * CCW Data Dictionary: ICD_DGNS_CD19</a>.
		 */
		ICD_DGNS_CD19,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_vrsn_cd19.txt">
		 * CCW Data Dictionary: ICD_DGNS_VRSN_CD19</a>.
		 */
		ICD_DGNS_VRSN_CD19,

		/**
		 * Type: <code>CHAR</code>, max chars: 19. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_cd20.txt">
		 * CCW Data Dictionary: ICD_DGNS_CD20</a>.
		 */
		ICD_DGNS_CD20,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_vrsn_cd20.txt">
		 * CCW Data Dictionary: ICD_DGNS_VRSN_CD20</a>.
		 */
		ICD_DGNS_VRSN_CD20,

		/**
		 * Type: <code>CHAR</code>, max chars: 19. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_cd21.txt">
		 * CCW Data Dictionary: ICD_DGNS_CD21</a>.
		 */
		ICD_DGNS_CD21,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_vrsn_cd21.txt">
		 * CCW Data Dictionary: ICD_DGNS_VRSN_CD21</a>.
		 */
		ICD_DGNS_VRSN_CD21,

		/**
		 * Type: <code>CHAR</code>, max chars: 19. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_cd22.txt">
		 * CCW Data Dictionary: ICD_DGNS_CD22</a>.
		 */
		ICD_DGNS_CD22,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_vrsn_cd22.txt">
		 * CCW Data Dictionary: ICD_DGNS_VRSN_CD22</a>.
		 */
		ICD_DGNS_VRSN_CD22,

		/**
		 * Type: <code>CHAR</code>, max chars: 19. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_cd23.txt">
		 * CCW Data Dictionary: ICD_DGNS_CD23</a>.
		 */
		ICD_DGNS_CD23,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_vrsn_cd23.txt">
		 * CCW Data Dictionary: ICD_DGNS_VRSN_CD23</a>.
		 */
		ICD_DGNS_VRSN_CD23,

		/**
		 * Type: <code>CHAR</code>, max chars: 19. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_cd24.txt">
		 * CCW Data Dictionary: ICD_DGNS_CD24</a>.
		 */
		ICD_DGNS_CD24,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_vrsn_cd24.txt">
		 * CCW Data Dictionary: ICD_DGNS_VRSN_CD24</a>.
		 */
		ICD_DGNS_VRSN_CD24,

		/**
		 * Type: <code>CHAR</code>, max chars: 19. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_cd25.txt">
		 * CCW Data Dictionary: ICD_DGNS_CD25</a>.
		 */
		ICD_DGNS_CD25,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_vrsn_cd25.txt">
		 * CCW Data Dictionary: ICD_DGNS_VRSN_CD25</a>.
		 */
		ICD_DGNS_VRSN_CD25,

		/**
		 * Type: <code>CHAR</code>, max chars: 7 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/fst_dgns_e_cd.txt">
		 * CCW Data Dictionary: FST_DGNS_E_CD</a>.
		 */
		FST_DGNS_E_CD,

		/**
		 * Type: <code>CHAR</code>, max chars: 1 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/fst_dgns_e_vrsn_cd.txt">
		 * CCW Data Dictionary: FST_DGNS_E_VRSN_CD</a>.
		 */
		FST_DGNS_E_VRSN_CD,

		/**
		 * Type: <code>CHAR</code>, max chars: 7 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_e_cd1.txt">
		 * CCW Data Dictionary: ICD_DGNS_E_CD1</a>.
		 */
		ICD_DGNS_E_CD1,

		/**
		 * Type: <code>CHAR</code>, max chars: 1 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_e_vrsn_cd1.txt">
		 * CCW Data Dictionary: ICD_DGNS_E_VRSN_CD1</a>.
		 */
		ICD_DGNS_E_VRSN_CD1,

		/**
		 * Type: <code>CHAR</code>, max chars: 7 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_e_cd2.txt">
		 * CCW Data Dictionary: ICD_DGNS_E_CD2</a>.
		 */
		ICD_DGNS_E_CD2,

		/**
		 * Type: <code>CHAR</code>, max chars: 1 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_e_vrsn_cd2.txt">
		 * CCW Data Dictionary: ICD_DGNS_E_VRSN_CD2</a>.
		 */
		ICD_DGNS_E_VRSN_CD2,

		/**
		 * Type: <code>CHAR</code>, max chars: 7 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_e_cd3.txt">
		 * CCW Data Dictionary: ICD_DGNS_E_CD3</a>.
		 */
		ICD_DGNS_E_CD3,

		/**
		 * Type: <code>CHAR</code>, max chars: 1 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_e_vrsn_cd3.txt">
		 * CCW Data Dictionary: ICD_DGNS_E_VRSN_CD3</a>.
		 */
		ICD_DGNS_E_VRSN_CD3,

		/**
		 * Type: <code>CHAR</code>, max chars: 7 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_e_cd4.txt">
		 * CCW Data Dictionary: ICD_DGNS_E_CD4</a>.
		 */
		ICD_DGNS_E_CD4,

		/**
		 * Type: <code>CHAR</code>, max chars: 1 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_e_vrsn_cd4.txt">
		 * CCW Data Dictionary: ICD_DGNS_E_VRSN_CD4</a>.
		 */
		ICD_DGNS_E_VRSN_CD4,

		/**
		 * Type: <code>CHAR</code>, max chars: 7 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_e_cd5.txt">
		 * CCW Data Dictionary: ICD_DGNS_E_CD5</a>.
		 */
		ICD_DGNS_E_CD5,

		/**
		 * Type: <code>CHAR</code>, max chars: 1 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_e_vrsn_cd5.txt">
		 * CCW Data Dictionary: ICD_DGNS_E_VRSN_CD5</a>.
		 */
		ICD_DGNS_E_VRSN_CD5,

		/**
		 * Type: <code>CHAR</code>, max chars: 7 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_e_cd6.txt">
		 * CCW Data Dictionary: ICD_DGNS_E_CD6</a>.
		 */
		ICD_DGNS_E_CD6,

		/**
		 * Type: <code>CHAR</code>, max chars: 1 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_e_vrsn_cd6.txt">
		 * CCW Data Dictionary: ICD_DGNS_E_VRSN_CD6</a>.
		 */
		ICD_DGNS_E_VRSN_CD6,

		/**
		 * Type: <code>CHAR</code>, max chars: 7 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_e_cd7.txt">
		 * CCW Data Dictionary: ICD_DGNS_E_CD7</a>.
		 */
		ICD_DGNS_E_CD7,

		/**
		 * Type: <code>CHAR</code>, max chars: 1 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_e_vrsn_cd7.txt">
		 * CCW Data Dictionary: ICD_DGNS_E_VRSN_CD7</a>.
		 */
		ICD_DGNS_E_VRSN_CD7,

		/**
		 * Type: <code>CHAR</code>, max chars: 7 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_e_cd8.txt">
		 * CCW Data Dictionary: ICD_DGNS_E_CD8</a>.
		 */
		ICD_DGNS_E_CD8,

		/**
		 * Type: <code>CHAR</code>, max chars: 1 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_e_vrsn_cd8.txt">
		 * CCW Data Dictionary: ICD_DGNS_E_VRSN_CD8</a>.
		 */
		ICD_DGNS_E_VRSN_CD8,

		/**
		 * Type: <code>CHAR</code>, max chars: 7 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_e_cd9.txt">
		 * CCW Data Dictionary: ICD_DGNS_E_CD9</a>.
		 */
		ICD_DGNS_E_CD9,

		/**
		 * Type: <code>CHAR</code>, max chars: 1 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_e_vrsn_cd9.txt">
		 * CCW Data Dictionary: ICD_DGNS_E_VRSN_CD9</a>.
		 */
		ICD_DGNS_E_VRSN_CD9,

		/**
		 * Type: <code>CHAR</code>, max chars: 7 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_e_cd10.txt">
		 * CCW Data Dictionary: ICD_DGNS_E_CD10</a>.
		 */
		ICD_DGNS_E_CD10,

		/**
		 * Type: <code>CHAR</code>, max chars: 1 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_e_vrsn_cd10.txt">
		 * CCW Data Dictionary: ICD_DGNS_E_VRSN_CD10</a>.
		 */
		ICD_DGNS_E_VRSN_CD10,

		/**
		 * Type: <code>CHAR</code>, max chars: 7 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_e_cd11.txt">
		 * CCW Data Dictionary: ICD_DGNS_E_CD11</a>.
		 */
		ICD_DGNS_E_CD11,

		/**
		 * Type: <code>CHAR</code>, max chars: 1 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_e_vrsn_cd11.txt">
		 * CCW Data Dictionary: ICD_DGNS_E_VRSN_CD11</a>.
		 */
		ICD_DGNS_E_VRSN_CD11,

		/**
		 * Type: <code>CHAR</code>, max chars: 7 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_e_cd12.txt">
		 * CCW Data Dictionary: ICD_DGNS_E_CD12</a>.
		 */
		ICD_DGNS_E_CD12,

		/**
		 * Type: <code>CHAR</code>, max chars: 1 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_dgns_e_vrsn_cd12.txt">
		 * CCW Data Dictionary: ICD_DGNS_E_VRSN_CD12</a>.
		 */
		ICD_DGNS_E_VRSN_CD12,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_cd1.txt">
		 * CCW Data Dictionary: ICD_PRCDR_CD1</a>.
		 */
		ICD_PRCDR_CD1,

		/**
		 * Type: <code>DATE</code>, max chars: 8. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_vrsn_cd1.txt">
		 * CCW Data Dictionary: ICD_PRCDR_VRSN_CD1</a>.
		 */
		ICD_PRCDR_VRSN_CD1,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/prcdr_dt1.txt">
		 * CCW Data Dictionary: PRCDR_DT1</a>.
		 */
		PRCDR_DT1,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_cd2.txt">
		 * CCW Data Dictionary: ICD_PRCDR_CD2</a>.
		 */
		ICD_PRCDR_CD2,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_vrsn_cd2.txt">
		 * CCW Data Dictionary: ICD_PRCDR_VRSN_CD2</a>.
		 */
		ICD_PRCDR_VRSN_CD2,

		/**
		 * Type: <code>DATE</code>, max chars: 8. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/prcdr_dt2.txt">
		 * CCW Data Dictionary: PRCDR_DT2</a>.
		 */
		PRCDR_DT2,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_cd3.txt">
		 * CCW Data Dictionary: ICD_PRCDR_CD3</a>.
		 */
		ICD_PRCDR_CD3,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_vrsn_cd3.txt">
		 * CCW Data Dictionary: ICD_PRCDR_VRSN_CD3</a>.
		 */
		ICD_PRCDR_VRSN_CD3,

		/**
		 * Type: <code>DATE</code>, max chars: 8. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/prcdr_dt3.txt">
		 * CCW Data Dictionary: PRCDR_DT3</a>.
		 */
		PRCDR_DT3,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_cd4.txt">
		 * CCW Data Dictionary: ICD_PRCDR_CD4</a>.
		 */
		ICD_PRCDR_CD4,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_vrsn_cd4.txt">
		 * CCW Data Dictionary: ICD_PRCDR_VRSN_CD4</a>.
		 */
		ICD_PRCDR_VRSN_CD4,

		/**
		 * Type: <code>DATE</code>, max chars: 8. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/prcdr_dt4.txt">
		 * CCW Data Dictionary: PRCDR_DT4</a>.
		 */
		PRCDR_DT4,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_cd5.txt">
		 * CCW Data Dictionary: ICD_PRCDR_CD5</a>.
		 */
		ICD_PRCDR_CD5,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_vrsn_cd5.txt">
		 * CCW Data Dictionary: ICD_PRCDR_VRSN_CD5</a>.
		 */
		ICD_PRCDR_VRSN_CD5,

		/**
		 * Type: <code>DATE</code>, max chars: 8. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/prcdr_dt5.txt">
		 * CCW Data Dictionary: PRCDR_DT5</a>.
		 */
		PRCDR_DT5,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_cd6.txt">
		 * CCW Data Dictionary: ICD_PRCDR_CD6</a>.
		 */
		ICD_PRCDR_CD6,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_vrsn_cd6.txt">
		 * CCW Data Dictionary: ICD_PRCDR_VRSN_CD6</a>.
		 */
		ICD_PRCDR_VRSN_CD6,

		/**
		 * Type: <code>DATE</code>, max chars: 8. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/prcdr_dt6.txt">
		 * CCW Data Dictionary: PRCDR_DT6</a>.
		 */
		PRCDR_DT6,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_cd7.txt">
		 * CCW Data Dictionary: ICD_PRCDR_CD7</a>.
		 */
		ICD_PRCDR_CD7,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_vrsn_cd7.txt">
		 * CCW Data Dictionary: ICD_PRCDR_VRSN_CD7</a>.
		 */
		ICD_PRCDR_VRSN_CD7,

		/**
		 * Type: <code>DATE</code>, max chars: 8. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/prcdr_dt7.txt">
		 * CCW Data Dictionary: PRCDR_DT7</a>.
		 */
		PRCDR_DT7,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_cd8.txt">
		 * CCW Data Dictionary: ICD_PRCDR_CD8</a>.
		 */
		ICD_PRCDR_CD8,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_vrsn_cd8.txt">
		 * CCW Data Dictionary: ICD_PRCDR_VRSN_CD8</a>.
		 */
		ICD_PRCDR_VRSN_CD8,

		/**
		 * Type: <code>DATE</code>, max chars: 8. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/prcdr_dt8.txt">
		 * CCW Data Dictionary: PRCDR_DT8</a>.
		 */
		PRCDR_DT8,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_cd9.txt">
		 * CCW Data Dictionary: ICD_PRCDR_CD9</a>.
		 */
		ICD_PRCDR_CD9,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_vrsn_cd9.txt">
		 * CCW Data Dictionary: ICD_PRCDR_VRSN_CD9</a>.
		 */
		ICD_PRCDR_VRSN_CD9,

		/**
		 * Type: <code>DATE</code>, max chars: 8. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/prcdr_dt9.txt">
		 * CCW Data Dictionary: PRCDR_DT9</a>.
		 */
		PRCDR_DT9,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_cd10.txt">
		 * CCW Data Dictionary: ICD_PRCDR_CD10</a>.
		 */
		ICD_PRCDR_CD10,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_vrsn_cd10.txt">
		 * CCW Data Dictionary: ICD_PRCDR_VRSN_CD10</a>.
		 */
		ICD_PRCDR_VRSN_CD10,

		/**
		 * Type: <code>DATE</code>, max chars: 8. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/prcdr_dt10.txt">
		 * CCW Data Dictionary: PRCDR_DT10</a>.
		 */
		PRCDR_DT10,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_cd11.txt">
		 * CCW Data Dictionary: ICD_PRCDR_CD11</a>.
		 */
		ICD_PRCDR_CD11,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_vrsn_cd11.txt">
		 * CCW Data Dictionary: ICD_PRCDR_VRSN_CD11</a>.
		 */
		ICD_PRCDR_VRSN_CD11,

		/**
		 * Type: <code>DATE</code>, max chars: 8. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/prcdr_dt11.txt">
		 * CCW Data Dictionary: PRCDR_DT11</a>.
		 */
		PRCDR_DT11,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_cd12.txt">
		 * CCW Data Dictionary: ICD_PRCDR_CD12</a>.
		 */
		ICD_PRCDR_CD12,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_vrsn_cd12.txt">
		 * CCW Data Dictionary: ICD_PRCDR_VRSN_CD12</a>.
		 */
		ICD_PRCDR_VRSN_CD12,

		/**
		 * Type: <code>DATE</code>, max chars: 8. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/prcdr_dt12.txt">
		 * CCW Data Dictionary: PRCDR_DT12</a>.
		 */
		PRCDR_DT12,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_cd13.txt">
		 * CCW Data Dictionary: ICD_PRCDR_CD13</a>.
		 */
		ICD_PRCDR_CD13,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_vrsn_cd13.txt">
		 * CCW Data Dictionary: ICD_PRCDR_VRSN_CD13</a>.
		 */
		ICD_PRCDR_VRSN_CD13,

		/**
		 * Type: <code>DATE</code>, max chars: 8. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/prcdr_dt13.txt">
		 * CCW Data Dictionary: PRCDR_DT13</a>.
		 */
		PRCDR_DT13,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_cd14.txt">
		 * CCW Data Dictionary: ICD_PRCDR_CD14</a>.
		 */
		ICD_PRCDR_CD14,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_vrsn_cd14.txt">
		 * CCW Data Dictionary: ICD_PRCDR_VRSN_CD14</a>.
		 */
		ICD_PRCDR_VRSN_CD14,

		/**
		 * Type: <code>DATE</code>, max chars: 8. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/prcdr_dt14.txt">
		 * CCW Data Dictionary: PRCDR_DT14</a>.
		 */
		PRCDR_DT14,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_c15.txt">
		 * CCW Data Dictionary: ICD_PRCDR_C15</a>.
		 */
		ICD_PRCDR_C15,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_vrsn_c15.txt">
		 * CCW Data Dictionary: ICD_PRCDR_VRSN_C15</a>.
		 */
		ICD_PRCDR_VRSN_C15,

		/**
		 * Type: <code>DATE</code>, max chars: 8. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/prcdr_dt15.txt">
		 * CCW Data Diction ary: PRCDR_DT15</a>.
		 */
		PRCDR_DT15,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_c16.txt">
		 * CCW Data Dictionary: ICD_PRCDR_C16</a>.
		 */
		ICD_PRCDR_C16,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_vrsn_c16.txt">
		 * CCW Data Dictionary: ICD_PRCDR_VRSN_C16</a>.
		 */
		ICD_PRCDR_VRSN_C16,

		/**
		 * Type: <code>DATE</code>, max chars: 8. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/prcdr_dt16.txt">
		 * CCW Data Dictionary: PRCDR_DT16</a>.
		 */
		PRCDR_DT16,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_cd17.txt">
		 * CCW Data Dictionary: ICD_PRCDR_CD17</a>.
		 */
		ICD_PRCDR_CD17,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_vrsn_cd17.txt">
		 * CCW Data Dictionary: ICD_PRCDR_VRSN_CD17</a>.
		 */
		ICD_PRCDR_VRSN_CD17,

		/**
		 * Type: <code>DATE</code>, max chars: 8. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/prcdr_dt17.txt">
		 * CCW Data Dictionary: PRCDR_DT17</a>.
		 */
		PRCDR_DT17,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_cd18.txt">
		 * CCW Data Dictionary: ICD_PRCDR_CD18</a>.
		 */
		ICD_PRCDR_CD18,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_vrsn_cd18.txt">
		 * CCW Data Dictionary: ICD_PRCDR_VRSN_CD18</a>.
		 */
		ICD_PRCDR_VRSN_CD18,

		/**
		 * Type: <code>DATE</code>, max chars: 8. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/prcdr_dt18.txt">
		 * CCW Data Dictionary: PRCDR_DT18</a>.
		 */
		PRCDR_DT18,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_cd19.txt">
		 * CCW Data Dictionary: ICD_PRCDR_CD19</a>.
		 */
		ICD_PRCDR_CD19,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_vrsn_cd19.txt">
		 * CCW Data Dictionary: ICD_PRCDR_VRSN_CD19</a>.
		 */
		ICD_PRCDR_VRSN_CD19,

		/**
		 * Type: <code>DATE</code>, max chars: 8. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/prcdr_dt19.txt">
		 * CCW Data Dictionary: PRCDR_DT19</a>.
		 */
		PRCDR_DT19,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_cd20.txt">
		 * CCW Data Dictionary: ICD_PRCDR_CD20</a>.
		 */
		ICD_PRCDR_CD20,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_vrsn_cd20.txt">
		 * CCW Data Dictionary: ICD_PRCDR_VRSN_CD20</a>.
		 */
		ICD_PRCDR_VRSN_CD20,

		/**
		 * Type: <code>DATE</code>, max chars: 8. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/prcdr_dt20.txt">
		 * CCW Data Dictionary: PRCDR_DT20</a>.
		 */
		PRCDR_DT20,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_cd21.txt">
		 * CCW Data Dictionary: ICD_PRCDR_CD21</a>.
		 */
		ICD_PRCDR_CD21,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_vrsn_cd21.txt">
		 * CCW Data Dictionary: ICD_PRCDR_VRSN_CD21</a>.
		 */
		ICD_PRCDR_VRSN_CD21,

		/**
		 * Type: <code>DATE</code>, max chars: 8. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/prcdr_dt21.txt">
		 * CCW Data Dictionary: PRCDR_DT21</a>.
		 */
		PRCDR_DT21,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_cd22.txt">
		 * CCW Data Dictionary: ICD_PRCDR_CD22</a>.
		 */
		ICD_PRCDR_CD22,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_vrsn_cd22.txt">
		 * CCW Data Dictionary: ICD_PRCDR_VRSN_CD22</a>.
		 */
		ICD_PRCDR_VRSN_CD22,

		/**
		 * Type: <code>DATE</code>, max chars: 8. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/prcdr_dt22.txt">
		 * CCW Data Dictionary: PRCDR_DT22</a>.
		 */
		PRCDR_DT22,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_cd23.txt">
		 * CCW Data Dictionary: ICD_PRCDR_CD23</a>.
		 */
		ICD_PRCDR_CD23,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_vrsn_cd23.txt">
		 * CCW Data Dictionary: ICD_PRCDR_VRSN_CD23</a>.
		 */
		ICD_PRCDR_VRSN_CD23,

		/**
		 * Type: <code>DATE</code>, max chars: 8. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/prcdr_dt23.txt">
		 * CCW Data Dictionary: PRCDR_DT23</a>.
		 */
		PRCDR_DT23,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_cd24.txt">
		 * CCW Data Dictionary: ICD_PRCDR_CD24</a>.
		 */
		ICD_PRCDR_CD24,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_vrsn_cd24.txt">
		 * CCW Data Dictionary: ICD_PRCDR_VRSN_CD24</a>.
		 */
		ICD_PRCDR_VRSN_CD24,

		/**
		 * Type: <code>DATE</code>, max chars: 8. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/prcdr_dt24.txt">
		 * CCW Data Dictionary: PRCDR_DT24</a>.
		 */
		PRCDR_DT24,

		/**
		 * Type: <code>CHAR</code>, max chars: 7. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_cd25.txt">
		 * CCW Data Dictionary: ICD_PRCDR_CD25</a>.
		 */
		ICD_PRCDR_CD25,

		/**
		 * Type: <code>CHAR</code>, max chars: 1. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/icd_prcdr_vrsn_cd25.txt">
		 * CCW Data Dictionary: ICD_PRCDR_VRSN_CD25</a>.
		 */
		ICD_PRCDR_VRSN_CD25,

		/**
		 * Type: <code>DATE</code>, max chars: 8. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/prcdr_dt25.txt">
		 * CCW Data Dictionary: PRCDR_DT25</a>.
		 */
		PRCDR_DT25,

		/**
		 * Type: <code>CHAR</code>, max chars: 7 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/rsn_visit_cd1.txt">
		 * CCW Data Dictionary: RSN_VISIT_CD1</a>.
		 */
		RSN_VISIT_CD1,

		/**
		 * Type: <code>CHAR</code>, max chars: 1 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/rsn_visit_vrsn_cd1.txt">
		 * CCW Data Dictionary: RSN_VISIT_VRSN_CD1</a>.
		 */
		RSN_VISIT_VRSN_CD1,

		/**
		 * Type: <code>CHAR</code>, max chars: 7 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/rsn_visit_cd2.txt">
		 * CCW Data Dictionary: RSN_VISIT_CD2</a>.
		 */
		RSN_VISIT_CD2,

		/**
		 * Type: <code>CHAR</code>, max chars: 1 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/rsn_visit_vrsn_cd2.txt">
		 * CCW Data Dictionary: RSN_VISIT_VRSN_CD2</a>.
		 */
		RSN_VISIT_VRSN_CD2,

		/**
		 * Type: <code>CHAR</code>, max chars: 7 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/rsn_visit_cd3.txt">
		 * CCW Data Dictionary: RSN_VISIT_CD3</a>.
		 */
		RSN_VISIT_CD3,

		/**
		 * Type: <code>CHAR</code>, max chars: 1 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/rsn_visit_vrsn_cd3.txt">
		 * CCW Data Dictionary: RSN_VISIT_VRSN_CD3</a>.
		 */
		RSN_VISIT_VRSN_CD3,
		
		/**
		 * Type: <code>NUM</code>, max chars: 12. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/ptb_ded.txt">
		 * CCW Data Dictionary: PTB_DED</a>.
		 */
		NCH_BENE_PTB_DDCTBL_AMT,

		/**
		 * Type: <code>NUM</code>, max chars: 12. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/ptb_coin.txt">
		 * CCW Data Dictionary: PTB_COIN</a>.
		 */
		NCH_BENE_PTB_COINSRNC_AMT,
		
		/**
		 * Type: <code>NUM</code>, max chars: 12. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/prvdrpmt.txt">
		 * CCW Data Dictionary: PRVDRPMT</a>.
		 */
		CLM_OP_PRVDR_PMT_AMT,

		
		/**
		 * Type: <code>NUM</code>, max chars: 12. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/benepmt.txt">
		 * CCW Data Dictionary: BENEPMT</a>.
		 */
		CLM_OP_BENE_PMT_AMT,

		/**
		 * Type: <code>NUM</code>, max chars: 13. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/clm_ln.txt">
		 * CCW Data Dictionary: CLM_LN</a>.
		 */
		CLM_LINE_NUM,
		
		REV_CNTR,
		REV_CNTR_DT,
		REV_CNTR_1ST_ANSI_CD,
		REV_CNTR_2ND_ANSI_CD,
		REV_CNTR_3RD_ANSI_CD,
		REV_CNTR_4TH_ANSI_CD,
		REV_CNTR_APC_HIPPS_CD,


		/**
		 * Type: <code>CHAR</code>, max chars: 1 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/hcpcs_cd.txt">
		 * CCW Data Dictionary: HCPCS_CD</a>.
		 */
		HCPCS_CD,

		/**
		 * Type: <code>CHAR</code>, max chars: 5 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/mdfr_cd1.txt">
		 * CCW Data Dictionary: MDFR_CD1</a>.
		 */
		HCPCS_1ST_MDFR_CD,

		/**
		 * Type: <code>CHAR</code>, max chars: 5 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/mdfr_cd2.txt">
		 * CCW Data Dictionary: MDFR_CD2</a>.
		 */
		HCPCS_2ND_MDFR_CD,

		REV_CNTR_PMT_MTHD_IND_CD,
		REV_CNTR_DSCNT_IND_CD,
		REV_CNTR_PACKG_IND_CD,
		REV_CNTR_OTAF_PMT_CD,

		/**
		 * Type: <code>CHAR</code>, max chars: 24 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/idendc.txt">
		 * CCW Data Dictionary: IDENDC</a>.
		 */
		REV_CNTR_IDE_NDC_UPC_NUM,

		REV_CNTR_UNIT_CNT,
		REV_CNTR_RATE_AMT,

		/**
		 * Type: <code>NUM</code>, max chars: 12. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/revblood.txt">
		 * CCW Data Dictionary: REVBLOOD</a>.
		 */
		REV_CNTR_BLOOD_DDCTBL_AMT,

		/**
		 * Type: <code>NUM</code>, max chars: 12. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/revdctbl.txt">
		 * CCW Data Dictionary: REVDCTBL</a>.
		 */
		REV_CNTR_CASH_DDCTBL_AMT,

		/**
		 * Type: <code>NUM</code>, max chars: 12. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/wageadj.txt">
		 * CCW Data Dictionary: WAGEADJ</a>.
		 */
		REV_CNTR_COINSRNC_WGE_ADJSTD_C,

		/**
		 * Type: <code>NUM</code>, max chars: 12. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/rdcdcoin.txt">
		 * CCW Data Dictionary: RDCDCOIN</a>.
		 */
		REV_CNTR_RDCD_COINSRNC_AMT,

		/**
		 * NOT MAPPED
		 * 
		 */
		REV_CNTR_1ST_MSP_PD_AMT,

		/**
		 * NOT MAPPED
		 * 
		 */
		REV_CNTR_2ND_MSP_PD_AMT,

		/**
		 * Type: <code>NUM</code>, max chars: 12. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/rprvdpmt.txt">
		 * CCW Data Dictionary: RPRVDPMT</a>.
		 */
		REV_CNTR_PRVDR_PMT_AMT,

		/**
		 * Type: <code>NUM</code>, max chars: 12. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/rbenepmt.txt">
		 * CCW Data Dictionary: RBENEPMT</a>.
		 */
		REV_CNTR_BENE_PMT_AMT,

		/**
		 * Type: <code>NUM</code>, max chars: 12. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/ptntresp.txt">
		 * CCW Data Dictionary: PTNTRESP</a>.
		 */
		REV_CNTR_PTNT_RSPNSBLTY_PMT,

		/**
		 * Type: <code>NUM</code>, max chars: 12. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/revpmt.txt">
		 * CCW Data Dictionary: REVPMT</a>.
		 */
		REV_CNTR_PMT_AMT_AMT,

		/**
		 * Type: <code>NUM</code>, max chars: 12. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/rev_chrg.txt">
		 * CCW Data Dictionary: REV_CHRG</a>.
		 */
		REV_CNTR_TOT_CHRG_AMT,

		/**
		 * Type: <code>NUM</code>, max chars: 12. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/rev_ncvr.txt">
		 * CCW Data Dictionary: REV_NCVR</a>.
		 */
		REV_CNTR_NCVRD_CHRG_AMT,

		REV_CNTR_STUS_IND_CD,

		/**
		 * Type: <code>NUM</code>, max chars: 10 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/rev_cntr_ndc_qty.txt">
		 * CCW Data Dictionary: REV_CNTR_NDC_QTY</a>.
		 */
		REV_CNTR_NDC_QTY,

		/**
		 * Type: <code>CHAR</code>, max chars: 2 <code>Optional</code>. See
		 * <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/rev_cntr_ndc_qty_qlfr_cd.txt">
		 * CCW Data Dictionary: REV_CNTR_NDC_QTY_QLFR_CD</a>.
		 */
		REV_CNTR_NDC_QTY_QLFR_CD,
		
		RNDRNG_PHYSN_UPIN,

		/**
		 * Type: <code>NUM</code>, max chars: 12. See <a href=
		 * "https://www.ccwdata.org/cs/groups/public/documents/datadictionary/rndrng_physn_npi.txt">
		 * CCW Data Dictionary: RNDRNG_PHYSN_NPI</a>.
		 */
		RNDRNG_PHYSN_NPI;
		
		public static String[] getColumnNames() {
			return Arrays.stream(values()).map(c -> c.name()).collect(Collectors.toList())
					.toArray(new String[values().length]);
		}
	}

	/**
	 * A simple helper app that will print out the {@link Column} entries as if
	 * they were a RIF header line, which can be used to help verify that the
	 * {@link Column} enum is correct.
	 * 
	 * @param args
	 *            (not used)
	 */
	public static void main(String[] args) {
		for (Column col : Column.values())
			System.out.print("|" + col);
	}
}
