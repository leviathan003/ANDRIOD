package com.example.myapplication;

import java.util.ArrayList;

public class specialistsLists {
    private ArrayList<Specialists> specialistsSG = new ArrayList<>();
    private ArrayList<Specialists> specialistsPH = new ArrayList<>();
    private ArrayList<Specialists> specialistsDT = new ArrayList<>();
    private ArrayList<Specialists> specialistsGN = new ArrayList<>();
    private ArrayList<Specialists> specialistsDN = new ArrayList<>();
    private ArrayList<Specialists> specialistsNL = new ArrayList<>();

    public specialistsLists() {
        specialistsSG.add(new Specialists("Dr. Sandeep Vaishya","20yrs","AIIMS","1234567890","400","https://www.spineandneurosurgeryhospitalindia.com/hospital/dr-sandeep-vaishya/images/consult-dr-sandeep-vaishya-best-gamma-knife-neurosurgeon-top-minimal-invasive-neuroslogist-fortis-hospital-delhi-india.jpg"));
        specialistsSG.add(new Specialists("Dr. Naresh Tehran","20yrs","AIIMS","1234567890","400","https://www.indiacardiacsurgerysite.com/medanta-hospital/doctors/dr-naresh-trehan/images/contact-dr-for-naresh-trehan-best-cardiac-surgeon-in-gurgaon-delhi-india.jpg"));
        specialistsSG.add(new Specialists("Dr. Arun Prasad","20yrs","AIIMS","1234567890","400","https://www.apollospectra.com/backend/web/doctor-images/1667470387Dr%20Arun%20Prasad.png"));
        specialistsSG.add(new Specialists("Dr. Rana Patir","20yrs","AIIMS","1234567890","400","https://static.medigence.com/uploads/doctor/images/536c1fe730ca39640c061e77597ac181.jpg"));
        specialistsSG.add(new Specialists("Dr. Sajan K Hedge","20yrs","AIIMS","1234567890","400","https://hbgmedicalassistance.com/wp-content/uploads/2019/08/Image-12-Dr.-sajan-Hegde-min.jpeg"));
        specialistsSG.add(new Specialists("Dr. Aditya Gupta","20yrs","AIIMS","1234567890","400","https://www.artemishospitals.com/BackEndImages/DoctorImage/dr-dr-aditya-gupta.jpg"));
        specialistsSG.add(new Specialists("Dr. Bipin Walia","20yrs","AIIMS","1234567890","400","https://static.medigence.com/uploads/doctor/images/2dc8c34a154481b0d0cc6ce87cc2442e.jpg"));
        specialistsSG.add(new Specialists("Dr. Hermant Sharma","20yrs","AIIMS","1234567890","400","https://www.spinecenter.in/wp-content/uploads/2022/11/dr-hemanth.jpg"));
        specialistsSG.add(new Specialists("Dr. IPS Oberoi","20yrs","AIIMS","1234567890","400","https://www.artemishospitals.com/BackEndImages/DoctorImage/dr-dr-i-p-s-oberoi.jpg"));
        specialistsSG.add(new Specialists("Dr. Subhash Chandra","20yrs","AIIMS","1234567890","400","https://static.medigence.com/uploads/doctor/images/e3d814204cdd2eeefb8a4dbf351eaf6b.jpg"));
        specialistsSG.add(new Specialists("Dr. Aashish Shah","20yrs","AIIMS","1234567890","400","https://static.medigence.com/uploads/doctor/images/23497ba2da201db6a45ffaf98ada1c4d.jpg"));
        specialistsSG.add(new Specialists("Dr. Abhijit Das","20yrs","AIIMS","1234567890","400","https://pbs.twimg.com/profile_images/1521074110289301506/W0Glv2mk_400x400.jpg"));
        specialistsSG.add(new Specialists("Dr. Abhishek Katha","20yrs","AIIMS","1234567890","400","https://cdn.askapollo.com/live/images/doctors/general-surgery/dr-katha-abhishek-general-surgery-in-hyderabad.png"));
        specialistsSG.add(new Specialists("Dr. M Kalyan Ravi Teja","20yrs","AIIMS","1234567890","400","https://indiacardiacsurgerysite.com/blog/wp-content/uploads/2020/04/Dr-Ritwick-Raj-Bhuyan.jpg"));
        specialistsSG.add(new Specialists("Dr. Anil Madupu","20yrs","AIIMS","1234567890","400","https://cdn.askapollo.com/live/images/doctors/general-and-laparoscopic-surgeon/dr-anil-malik-general-and-laparoscopic-surgeon-in-delhi..png"));

        specialistsPH = specialistsSG;
        specialistsDN = specialistsSG;
        specialistsGN = specialistsSG;
        specialistsDT = specialistsSG;
        specialistsNL = specialistsSG;
    }

    public ArrayList<Specialists> getSpecialistsSG() {
        return specialistsSG;
    }

    public ArrayList<Specialists> getSpecialistsPH() {
        return specialistsPH;
    }

    public ArrayList<Specialists> getSpecialistsDT() {
        return specialistsDT;
    }

    public ArrayList<Specialists> getSpecialistsGN() {
        return specialistsGN;
    }

    public ArrayList<Specialists> getSpecialistsDN() {
        return specialistsDN;
    }

    public ArrayList<Specialists> getSpecialistsNL() {
        return specialistsNL;
    }
}
