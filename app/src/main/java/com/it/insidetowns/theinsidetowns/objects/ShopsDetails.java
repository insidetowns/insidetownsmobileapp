package com.it.insidetowns.theinsidetowns.objects;

public class ShopsDetails
{
    private String customer_id;
    private String employee_id;

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getEmi_number() {
        return emi_number;
    }

    public void setEmi_number(String emi_number) {
        this.emi_number = emi_number;
    }

    public String getPrinciple() {
        return principle;
    }

    public void setPrinciple(String principle) {
        this.principle = principle;
    }

    public String getIntrest() {
        return intrest;
    }

    public void setIntrest(String intrest) {
        this.intrest = intrest;
    }

    public String getBalance_amount() {
        return balance_amount;
    }

    public void setBalance_amount(String balance_amount) {
        this.balance_amount = balance_amount;
    }

    public String getCarry_forward_date() {
        return carry_forward_date;
    }

    public void setCarry_forward_date(String carry_forward_date) {
        this.carry_forward_date = carry_forward_date;
    }

    public String getNo_of_late_days() {
        return no_of_late_days;
    }

    public void setNo_of_late_days(String no_of_late_days) {
        this.no_of_late_days = no_of_late_days;
    }

    public String getPenality_charges_perday() {
        return penality_charges_perday;
    }

    public void setPenality_charges_perday(String penality_charges_perday) {
        this.penality_charges_perday = penality_charges_perday;
    }

    public String getPaid_amount_total() {
        return paid_amount_total;
    }

    public void setPaid_amount_total(String paid_amount_total) {
        this.paid_amount_total = paid_amount_total;
    }

    public String getPstatus_id() {
        return pstatus_id;
    }

    public void setPstatus_id(String pstatus_id) {
        this.pstatus_id = pstatus_id;
    }

    public String getCollect_type_id() {
        return collect_type_id;
    }

    public void setCollect_type_id(String collect_type_id) {
        this.collect_type_id = collect_type_id;
    }

    public String getPayment_type_id() {
        return payment_type_id;
    }

    public void setPayment_type_id(String payment_type_id) {
        this.payment_type_id = payment_type_id;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public String getPerson_mobile() {
        return person_mobile;
    }

    public void setPerson_mobile(String person_mobile) {
        this.person_mobile = person_mobile;
    }

    public String getPaid_on_full_date() {
        return paid_on_full_date;
    }

    public void setPaid_on_full_date(String paid_on_full_date) {
        this.paid_on_full_date = paid_on_full_date;
    }

    public String getPaid_on_full_time() {
        return paid_on_full_time;
    }

    public void setPaid_on_full_time(String paid_on_full_time) {
        this.paid_on_full_time = paid_on_full_time;
    }

    public String getPayment_date() {
        return payment_date;
    }

    public void setPayment_date(String payment_date) {
        this.payment_date = payment_date;
    }

    public String getPayment_month() {
        return payment_month;
    }

    public void setPayment_month(String payment_month) {
        this.payment_month = payment_month;
    }

    public String getPayment_year() {
        return payment_year;
    }

    public void setPayment_year(String payment_year) {
        this.payment_year = payment_year;
    }

    private String amount;
    private String emi_number;
    private String principle;
    private String intrest;
    private String balance_amount;

    public String getBlnc_without_carry() {
        return blnc_without_carry;
    }

    public void setBlnc_without_carry(String blnc_without_carry) {
        this.blnc_without_carry = blnc_without_carry;
    }

    public String getFore_closing_blnc() {
        return fore_closing_blnc;
    }

    public void setFore_closing_blnc(String fore_closing_blnc) {
        this.fore_closing_blnc = fore_closing_blnc;
    }

    private String blnc_without_carry;
    private String fore_closing_blnc;

    private String carry_forward_date;
    private String no_of_late_days;
    private String penality_charges_perday;
    private String paid_amount_total;
    private String pstatus_id;
    private String collect_type_id;
    private String payment_type_id;
    private String person_name;
    private String person_mobile;
    private String paid_on_full_date;
    private String paid_on_full_time;
    private String payment_date;
    private String payment_month;
    private String payment_year;

    public String getLast_emi_date() {
        return last_emi_date;
    }

    public void setLast_emi_date(String last_emi_date) {
        this.last_emi_date = last_emi_date;
    }

    private String last_emi_date;


    public ShopsDetails(	String customer_id,
                                   String employee_id,
                                   String amount,
                                   String emi_number,
                                   String principle,
                                   String intrest,
                                   String balance_amount,
                              String blnc_without_carry,
                              String fore_closing_blnc,

                                   String carry_forward_date,
                              String last_emi_date,
                                   String no_of_late_days,
                                   String penality_charges_perday,
                                   String paid_amount_total,
                                   String pstatus_id,
                                   String collect_type_id,
                                   String payment_type_id,
                                   String person_name,
                                   String person_mobile,
                                   String paid_on_full_date,
                                   String paid_on_full_time,
                                   String payment_date,
                                   String payment_month,
                                   String payment_year		) {
        this.customer_id=		 customer_id;
        this.employee_id=		 employee_id;
        this.amount= 			 amount;
        this.emi_number= 		 emi_number;
        this.principle=		 principle;
        this.intrest=		 intrest;
        this.balance_amount= 	 balance_amount;

        this.blnc_without_carry=		 blnc_without_carry;
        this.fore_closing_blnc= 	 fore_closing_blnc;

        this.carry_forward_date= 	 carry_forward_date;
        this.last_emi_date =last_emi_date;
        this.no_of_late_days= 		 no_of_late_days;
        this.penality_charges_perday=  penality_charges_perday;
        this.paid_amount_total= 		 paid_amount_total;
        this.pstatus_id= 				 pstatus_id;
        this.collect_type_id= 		 collect_type_id;
        this.payment_type_id= 		 payment_type_id;
        this.person_name= 			 person_name;
        this.person_mobile= 			 person_mobile;
        this.paid_on_full_date= 		 paid_on_full_date;
        this.paid_on_full_time= 		 paid_on_full_time;
        this.payment_date= 			 payment_date;
        this.payment_month= 			 payment_month;
        this.payment_year=		 payment_year	;
    }


   @Override
    public String toString()
    {
        return "ClassPojo [customer_id = "+customer_id+
                ", employee_id = "+employee_id+
                ", amount = "+amount+
                ", emi_number = "+emi_number+
                ", principle = "+principle+
                ", intrest = "+intrest+
                ", balance_amount= "+ 	 balance_amount+
                ", blnc_without_carry = "+blnc_without_carry+
                ", fore_closing_blnc= "+ 	 fore_closing_blnc+
                ", carry_forward_date= "+ 	 carry_forward_date+
                ", last_emi_date= "+ 	 last_emi_date+
                ", no_of_late_days= "+ 		 no_of_late_days+
                ", penality_charges_perday= "+  penality_charges_perday+
                ", paid_amount_total= "+ 		 paid_amount_total+
                ", pstatus_id= "+ 				 pstatus_id+
                ", collect_type_id= "+ 		 collect_type_id+
                ", payment_type_id= "+ 		 payment_type_id+
                ", person_name= "+ 			 person_name+
                ", person_mobile= "+ 			 person_mobile+
                ", paid_on_full_date= "+ 		 paid_on_full_date+
                ", paid_on_full_time= "+ 		 paid_on_full_time+
                ", payment_date= "+ 			 payment_date+
                ", payment_month= "+ 			 payment_month+
                ", payment_year= "+		 payment_year	+
                "]";
    }


}