package com.example.rixwansharif.travelanch_company;

/**
 * Created by Rixwan Sharif on 7/15/2017.
 */
public class config {


    public static final String Login_URL = "http://rixwanxharif.000webhostapp.com/company/login.php";

    public static final String SendOtp_URL = "http://rixwanxharif.000webhostapp.com/company/sendotp.php";

    public static final String Fetch_Trip_URL = "http://rixwanxharif.000webhostapp.com/company/client_trips.php";

    public static final String Company_Bids_URL = "http://rixwanxharif.000webhostapp.com/company/company_bids.php";

    public static final String Accepted_Company_Bids_URL = "http://rixwanxharif.000webhostapp.com/company/accepted_company_bids.php";

    public static final String Bachat_Bid_URL = "http://rixwanxharif.000webhostapp.com/company/bachat_bid.php";

    public static final String LambSamb_Bid_URL = "http://rixwanxharif.000webhostapp.com/company/lamb_samb_bid.php";

    public static final String Company_Vehicles_URL = "http://rixwanxharif.000webhostapp.com/company/company_vehicles.php";

    public static final String Company_Drivers_URL = "http://rixwanxharif.000webhostapp.com/company/company_drivers.php";




    public static final String Delete_Company_Bid_URL = "http://rixwanxharif.000webhostapp.com/company/delete_company_bid.php";
    public static final String Edit_Company_Bid_URL = "http://rixwanxharif.000webhostapp.com/company/edit_company_bid.php";
    //Keys for Sharedpreferences
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "Travelanche_Company";

    //This would be used to store the phone of current logged in user
    public static final String Phone_SHARED_PREF = "phone";
    public static final String Company_SHARED_PREF = "company";
    public static final String Company_RegNo_SHARED_PREF = "regno";
    public static final String Contact_Person_SHARED_PREF = "contact_person";
    public static final String City_SHARED_PREF = "city";
    public static final String Rating_SHARED_PREF = "rating";
    public static final String Address_SHARED_PREF = "address";
    public static final String Image_SHARED_PREF = "image";
    public static final String Device_Token= "device";

    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";



}
