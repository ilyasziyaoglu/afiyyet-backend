package com.afiyyet.brand.db.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-10-09
 */

public enum FeatureType {
	QR_MENU(0, "QR ile menüye erişme"),
	QR_WIFI(1, "QR ile restoran internetine bağlanma"),
	CRUD_OPERATIONS(2, "Menü oluşturma ve güncelleme"),
	ORDERING(3, "Ürün ve kategorileri sıralama"),
	EDUCATION(4, "Yönetim paneli eğitimi"),
	SUPPORT(5, "7 / 24 Kesintisiz Destek"),
	LIKE(6, "Ürün beğenme"),
	MOST_POPULAR(6, "En çok beğenilenler"),
	FAVORITE(7, "Favorilere ekleme"),
	CAMPAIGN(8, "Kampanya oluşturma"),
	FEEDBACKS(10, "Kolay geri bildirim"),
	COUPONS(11, "İndirim Kuponu Oluşturma"),
	RESERVATIONS(12, "Rezervasyon Alma"),
	SOCIAL_MEDIA_SHARE(13, "Sosyal medya paylaşımı"),
	REPORTS(14, "Kullanıcı ve Menü raporları"),
	ANALYSIS(15, "Kullanıcı ve Menü analizleri"),
	HAPPINESS(16, "Mutluluk seviyesi");


	FeatureType(Integer value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	private Integer value;
	private String desc;

	public String getDesc() {
		return desc;
	}

	public Integer getValue() {
		return value;
	}

	@JsonCreator
	public static FeatureType getEnumFromValue(Integer value) {
		for (FeatureType featureType : values()) {
			if (featureType.getValue().equals(value)) {
				return featureType;
			}
		}
		return null;
	}

	public static List<FeatureType> getStd() {
		List<FeatureType> featureTypeList = new ArrayList<>();
		featureTypeList.add(QR_MENU);
		featureTypeList.add(QR_WIFI);
		featureTypeList.add(CRUD_OPERATIONS);
		featureTypeList.add(ORDERING);
		featureTypeList.add(EDUCATION);
		featureTypeList.add(SUPPORT);
		return featureTypeList;
	}

	public static List<FeatureType> getPro() {
		List<FeatureType> featureTypeList = getStd();
		featureTypeList.add(LIKE);
		featureTypeList.add(MOST_POPULAR);
		featureTypeList.add(FAVORITE);
		featureTypeList.add(CAMPAIGN);
		featureTypeList.add(FEEDBACKS);
		return featureTypeList;
	}

	public static List<FeatureType> getVip() {
		List<FeatureType> featureTypeList = getPro();
		featureTypeList.add(COUPONS);
		featureTypeList.add(RESERVATIONS);
		featureTypeList.add(SOCIAL_MEDIA_SHARE);
		featureTypeList.add(REPORTS);
		featureTypeList.add(ANALYSIS);
		featureTypeList.add(HAPPINESS);
		return featureTypeList;
	}
}
