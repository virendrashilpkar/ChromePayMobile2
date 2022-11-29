package com.chromepaymobile.Api.Network;

public class AllUrl {

    public static final String BaseUrl = "http://ec2-user@ec2-13-233-63-235.ap-south-1.compute.amazonaws.com:3300/";

//    public static final String BaseUrl = "http://192.168.1.158:3300/";

    public static final String Login = BaseUrl+"Agentlogin";

    public static final String Register = BaseUrl+"createCustomerByOrg1/";

    public static final String VerifyCustomerAgent = BaseUrl+"verifyCustomer";

    public static final String CustomerDashBoard = BaseUrl+"custdetail/";

    public static final String ChangeAgentPass = BaseUrl+"agentchangePassword/";

    public static final String AgentProfile = BaseUrl+"agentProfile/";

    public static final String UpdateProfile = BaseUrl+"agentProfileUpdate/";

    public static final String AgentCommission = BaseUrl+"commissionlist/";

    public static final String AllDid = BaseUrl+"agentcustomerList/";

    public static final String AwatingDid = BaseUrl+"AgentAwaiting/";

    public static final String BlockedDid = BaseUrl+"agent_blocked/";

    public static final String ForgotPassWord = BaseUrl+"AgentforgotPassword/";

    public static final String VerifyOtpEmail = BaseUrl+"AgentForgetPassVerifyOtp";

    public static final String ChangePassWord = BaseUrl+"AgentchangePassword";

    public static final String VerifyCustomer = BaseUrl+"customerVerifyByAgent/";

    public static final String DeleteCustomer = BaseUrl+"deleteCustomeragent/";

    public static final String UnBlockedCustomer = BaseUrl+"unSuspengCustomer/";

    public static final String AgentPerformanceReport = BaseUrl+"agentAddCustByMonth/";

    public static final String AgentDashInfo = BaseUrl+"agentDash/";

    public static final String GetOrgApi = BaseUrl+"getOrgForLoan/";

    public static final String GetLoanApi = BaseUrl+"getOrgLoans/";

    public static final String GetLoanInterest = BaseUrl+"getInterestOFLoan/";

    public static final String LoanOTP = BaseUrl+"send_Loan_Otp/";

    public static final String CalculateAmount = BaseUrl+"calculate_Amount/";

    public static final String CustomerLoanApply = BaseUrl+"Cust_apply_Agent_Loans/";

    public static final String LoanPass = BaseUrl+"get_Agent_pass_Loans/";

    public static final String Installment = BaseUrl+"get_Insatallment_Loans/";

    public static final String NextDueAmount = BaseUrl+"get_next_month_emi/";

    public static final String PayEmi = BaseUrl+"pay_cust_emi/";

    public static final String OrganisationList = BaseUrl+"orgList";

    public static final String LinkedServicesOTP = BaseUrl+"Cust_Linked_Srevice_send_OTP";

    public static final String CustomerLinked = BaseUrl+"Cust_Linked_Srevice";

    public static final String FaceDetect = BaseUrl+"cust_Face_ditect/";
}
