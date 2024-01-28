package com.example.myapplication;

import java.util.ArrayList;

public class HealthArticles {
    private ArrayList<HealthArticle> articles = new ArrayList<>();

    public HealthArticles() {
        articles.add(new HealthArticle("What are zombie viruses scientists are worried about?","https://timesofindia.indiatimes.com/life-style/health-fitness/health-news/what-are-the-zombie-viruses-scientists-are-worried-about-is-it-likely-to-start-another-pandemic/photostory/107171595.cms"));
        articles.add(new HealthArticle("Ginger water vs Ginger tea\nwhich is better?","https://timesofindia.indiatimes.com/life-style/health-fitness/health-news/ginger-water-vs-ginger-tea-which-is-better-for-your-health/photostory/107168000.cms"));
        articles.add(new HealthArticle("Cervical Cancer: Steps to minimise","https://timesofindia.indiatimes.com/life-style/health-fitness/health-news/cervical-cancer-steps-to-minimize-the-risk-and-protect-your-health/articleshow/107167119.cms"));
        articles.add(new HealthArticle("Low sodium high potassium diet benefits","https://timesofindia.indiatimes.com/life-style/health-fitness/health-news/low-sodium-high-potassium-diet-can-bring-down-bp-know-what-this-means/photostory/107165853.cms"));
        articles.add(new HealthArticle("7 ways to stay happy and positive","https://timesofindia.indiatimes.com/life-style/health-fitness/health-news/7-simple-ways-to-stay-happy-and-positive-improve-your-mental-health/articleshow/107150597.cms"));
        articles.add(new HealthArticle("All about Zone 2 training","https://timesofindia.indiatimes.com/life-style/health-fitness/health-news/all-you-need-to-know-about-zone-2-training/articleshow/107150115.cms"));
        articles.add(new HealthArticle("Couple yoga poses for weight loss","https://timesofindia.indiatimes.com/life-style/health-fitness/health-news/couple-yoga-poses-for-weight-loss-easiest-but-effective-yoga-asanas-to-try-with-your-partner/articleshow/107170133.cms"));
        articles.add(new HealthArticle("Pap smear tests needed even after vaccination","https://timesofindia.indiatimes.com/life-style/health-fitness/health-news/why-pap-smear-tests-are-necessary-to-take-even-after-hpv-vaccination/articleshow/107167746.cms"));
        articles.add(new HealthArticle("Kidney Stones: Myths vs Facts","https://timesofindia.indiatimes.com/life-style/health-fitness/health-news/kidney-stones-separating-myths-from-facts/photostory/107166666.cms"));
        articles.add(new HealthArticle("Tulsi benefits","https://timesofindia.indiatimes.com/life-style/health-fitness/health-news/tulsi-benefits-heres-why-this-powerful-herb-should-be-a-part-of-your-routine/articleshow/107148654.cms"));
        articles.add(new HealthArticle("How to treat forehead acne and pimples","https://timesofindia.indiatimes.com/life-style/health-fitness/health-news/how-to-treat-forehead-acne-and-pimples-effectively/photostory/107143442.cms"));
        articles.add(new HealthArticle("What happens when BP falls?","https://timesofindia.indiatimes.com/life-style/health-fitness/health-news/what-happens-when-bp-falls-doctor-explains-the-potential-risks/articleshow/107144802.cms"));
        articles.add(new HealthArticle("All about reverse\nwalking","https://timesofindia.indiatimes.com/life-style/health-fitness/health-news/reverse-walking-the-latest-fitness-trend/articleshow/107144268.cms"));
    }

    public ArrayList<HealthArticle> getArticles() {
        return articles;
    }


}
