package com.acb.bakewellgps.modell;

public class sentShopAddDetails {

    public String short_name;
    public String organisation_name;
    public String tax_number;
    public int province_id;
    public String post_box_number;
    public String address_line1;
    public String address_line2;
    public String address_line3;
    public String email;
    public String website;
    public String mobile_no1;
    public String mobile_no2;
    public String whatsapp_no;
    public String phone_no;
    public int shop_category_id;
    public String transaction_type;
    public String licence_no;
    public String owner_name;
    public String owner_mobile_no;
    public String owner_email_id;
    public String shop_contact_name;
    public String shop_contact_mobile_no;
    public String shop_contact_email_id;
    public int credit_days;
    public String latitude;
    public String longitude;
    public String shop_logo;
    public String shop_image;
    public int estd_year;
    public String landmark;

    public sentShopAddDetails(String short_name,
                              String organisation_name,
                              String tax_number,
                              int province_id,
                              String post_box_number,
                              String address_line1,
                              String address_line2,
                              String address_line3,
                              String email,
                              String website,
                              String mobile_no1,
                              String mobile_no2,
                              String whatsapp_no,
                              String phone_no,
                              int shop_category_id,
                              String transaction_type,
                              String licence_no,
                              String owner_name,
                              String owner_mobile_no,
                              String owner_email_id,
                              String shop_contact_name,
                              String shop_contact_mobile_no,
                              String shop_contact_email_id,
                              int credit_days,
                              String latitude,
                              String longitude,
                              String shop_logo,
                              String shop_image,
                              int estd_year,
                              String landmark) {
        this.short_name = short_name;
        this.organisation_name = organisation_name;
        this.tax_number = tax_number;
        this.province_id = province_id;
        this.post_box_number = post_box_number;
        this.address_line1 = address_line1;
        this.address_line2 = address_line2;
        this.address_line3 = address_line3;
        this.email = email;
        this.website = website;
        this.mobile_no1 = mobile_no1;
        this.mobile_no2 = mobile_no2;
        this.whatsapp_no = whatsapp_no;
        this.phone_no = phone_no;
        this.shop_category_id = shop_category_id;
        this.transaction_type = transaction_type;
        this.licence_no = licence_no;
        this.owner_name = owner_name;
        this.owner_mobile_no = owner_mobile_no;
        this.owner_email_id = owner_email_id;
        this.shop_contact_name = shop_contact_name;
        this.shop_contact_mobile_no = shop_contact_mobile_no;
        this.shop_contact_email_id = shop_contact_email_id;
        this.credit_days = credit_days;
        this.latitude = latitude;
        this.longitude = longitude;
        this.shop_logo = shop_logo;
        this.shop_image = shop_image;
        this.estd_year = estd_year;
        this.landmark = landmark;
    }
}
