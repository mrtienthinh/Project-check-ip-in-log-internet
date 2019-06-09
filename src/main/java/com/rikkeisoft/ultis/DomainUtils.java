package com.rikkeisoft.ultis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DomainUtils {
	public static List<String> getIpList(String domainName) throws IOException{
		// Check domain;
		if (domainName.contains("://")) {
			domainName = domainName.split("://")[1];
		}
		
		while(domainName.contains("/")) {
			domainName = domainName.replaceAll("/", "");
		}
		
		// Crawler data form website;
		String urlgetIpByDomainName = "https://www.robtex.com/dns-lookup/";
		Set<String> ipSet = new HashSet<>();
		Document doc = Jsoup.connect(urlgetIpByDomainName+domainName).get();
		
		//Get all element of tag <a> contain ip;
		Elements elements = doc.getElementsByAttributeValueContaining("href", "https://www.robtex.com/ip-lookup/");
		
		// Get value ip of tag <a> then add to list;
		for (Element element : elements) {
			ipSet.add(element.text());
		}
		
		List<String> ipList = new ArrayList<>(ipSet);
		return ipList;
	}
}
