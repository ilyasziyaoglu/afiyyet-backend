package com.afiyyet.common.user.db.entity;


import com.afiyyet.brand.db.entity.Brand;
import com.afiyyet.client.user.Gender;
import com.afiyyet.common.basemodel.db.entity.AbstractBaseEntity;
import com.afiyyet.common.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ilyas Ziyaoglu
 * @date 2020-04-18
 */

@Entity
@ToString
@RequiredArgsConstructor
@Table(name = "users")
public class User extends AbstractBaseEntity implements UserDetails {

	private static final long serialVersionUID = 3842413573148772459L;

	@Id
	@Column(name = "id", nullable = false)
	@SequenceGenerator(name = "user_id_gen", sequenceName = "user_id_seq", allocationSize = 1)
	@GeneratedValue(generator = "user_id_gen", strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(name = "email")
	private String email;

	@Column(name = "username")
	private String username;

	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;

	@Column(name = "password")
	private String password;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "image_url")
	private String imageUrl;

	@Column(name = "gender")
	private Gender gender;

	@Column(name = "phone")
	private String phone;

	@Column(name = "birthdate")
	private Date birthdate;

	@Column(name = "country")
	private String country;

	@Column(name = "addresses", length = 10000)
	private String addresses;

	@Column(name = "status")
	private Status status;

	@Column(name = "roles")
	private String roles;

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		//TODO bu kismi ayarla kardesim yeter artik
		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.stream(roles.split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	public List<String> getRoles() {
		return Arrays.asList(roles.split(","));
	}

	public void setRoles(List<String> roles) {
		this.roles = String.join(",", roles);
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getAddresses() {
		return addresses;
	}

	public void setAddresses(String addresses) {
		this.addresses = addresses;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
}
