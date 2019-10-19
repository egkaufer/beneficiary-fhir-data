/*
 * Contains the DB model structs for use with Diesel. Originally generated by
 * <https://crates.io/crates/diesel_cli_ext>.
 */

#![allow(unused)]
#![allow(clippy::all)]

use bigdecimal::BigDecimal;
use chrono::NaiveDate;

/// Represents records returned from the `claims_partd` table.
#[derive(Queryable, Debug)]
pub struct PartDEvent {
    pub PDE_ID: String,
    pub ADJSTMT_DLTN_CD: Option<String>,
    pub BENE_ID: String,
    pub BRND_GNRC_CD: Option<String>,
    pub CTSTRPHC_CVRG_CD: Option<String>,
    pub CLM_GRP_ID: BigDecimal,
    pub CMPND_CD: i32,
    pub DAYS_SUPLY_NUM: BigDecimal,
    pub DAW_PROD_SLCTN_CD: String,
    pub DSPNSNG_STUS_CD: Option<String>,
    pub DRUG_CVRG_STUS_CD: String,
    pub FILL_NUM: BigDecimal,
    pub RPTD_GAP_DSCNT_NUM: BigDecimal,
    pub GDC_ABV_OOPT_AMT: BigDecimal,
    pub GDC_BLW_OOPT_AMT: BigDecimal,
    pub LICS_AMT: BigDecimal,
    pub PROD_SRVC_ID: String,
    pub NSTD_FRMT_CD: Option<String>,
    pub OTHR_TROOP_AMT: BigDecimal,
    pub CVRD_D_PLAN_PD_AMT: BigDecimal,
    pub NCVRD_PLAN_PD_AMT: BigDecimal,
    pub PLRO_AMT: BigDecimal,
    pub PTNT_PAY_AMT: BigDecimal,
    pub PTNT_RSDNC_CD: String,
    pub PD_DT: Option<NaiveDate>,
    pub PHRMCY_SRVC_TYPE_CD: String,
    pub PLAN_PBP_REC_NUM: String,
    pub PLAN_CNTRCT_REC_ID: String,
    pub PRSCRBR_ID: String,
    pub PRSCRBR_ID_QLFYR_CD: String,
    pub SRVC_DT: NaiveDate,
    pub RX_ORGN_CD: Option<String>,
    pub RX_SRVC_RFRNC_NUM: BigDecimal,
    pub PRCNG_EXCPTN_CD: Option<String>,
    pub QTY_DSPNSD_NUM: BigDecimal,
    pub SRVC_PRVDR_ID: String,
    pub SRVC_PRVDR_ID_QLFYR_CD: String,
    pub SUBMSN_CLR_CD: Option<String>,
    pub TOT_RX_CST_AMT: BigDecimal,
    pub FINAL_ACTION: String,
}
