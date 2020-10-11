package com.smartmenu.brand.db.model;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;
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
	public static FeatureType getEnumFromValue(String value) {
		for (FeatureType featureType : values()) {
			if (value.equals(featureType.name())) {
				return featureType;
			}
		}
		return null;
	}

	public static List<FeatureType> getStd() {
		return Arrays.asList(FeatureType.LIKE, FeatureType.MOST_POPULAR, FeatureType.FAVORITE, FeatureType.CAMPAIGN, FeatureType.FEEDBACKS);
	}

	public static List<FeatureType> getPro() {
		List<FeatureType> featureTypeList = getStd();
		featureTypeList.addAll(Arrays.asList(FeatureType.LIKE, FeatureType.MOST_POPULAR, FeatureType.FAVORITE, FeatureType.CAMPAIGN, FeatureType.FEEDBACKS));
		return featureTypeList;
	}

	public static List<FeatureType> getVip() {
		List<FeatureType> featureTypeList = getStd();
		featureTypeList.addAll(Arrays.asList(FeatureType.COUPONS, FeatureType.RESERVATIONS, FeatureType.SOCIAL_MEDIA_SHARE, FeatureType.REPORTS, FeatureType.ANALYSIS, FeatureType.HAPPINESS));
		return featureTypeList;
	}
}
