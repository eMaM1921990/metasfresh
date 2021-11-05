/*
 * Patient - Warenwirtschaft (Basis)
 * Synchronisation der Patienten mit der Warenwirtschaft
 *
 * OpenAPI spec version: 1.0.7
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package io.swagger.client.model;

import java.util.Objects;
import java.util.Arrays;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import io.swagger.client.model.CareGiver;
import io.swagger.client.model.PatientBillingAddress;
import io.swagger.client.model.PatientDeliveryAddress;
import io.swagger.client.model.PatientHospital;
import io.swagger.client.model.PatientPayer;
import io.swagger.client.model.PatientPrimaryDoctorInstitution;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.threeten.bp.LocalDate;
import org.threeten.bp.OffsetDateTime;
/**
 * Patient
 */

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-11-05T09:14:03.653Z[GMT]")
public class Patient {
  @SerializedName("_id")
  private UUID _id = null;

  @SerializedName("customerId")
  private String customerId = null;

  @SerializedName("customerContactId")
  private String customerContactId = null;

  @SerializedName("gender")
  private BigDecimal gender = null;

  @SerializedName("title")
  private BigDecimal title = null;

  @SerializedName("firstName")
  private String firstName = null;

  @SerializedName("lastName")
  private String lastName = null;

  @SerializedName("birthday")
  private LocalDate birthday = null;

  @SerializedName("address")
  private String address = null;

  @SerializedName("additionalAddress")
  private String additionalAddress = null;

  @SerializedName("additionalAddress2")
  private String additionalAddress2 = null;

  @SerializedName("postalCode")
  private String postalCode = null;

  @SerializedName("city")
  private String city = null;

  @SerializedName("phone")
  private String phone = null;

  @SerializedName("fax")
  private String fax = null;

  @SerializedName("mobilePhone")
  private String mobilePhone = null;

  @SerializedName("email")
  private String email = null;

  @SerializedName("primaryDoctorId")
  private String primaryDoctorId = null;

  @SerializedName("primaryDoctorInstitution")
  private PatientPrimaryDoctorInstitution primaryDoctorInstitution = null;

  @SerializedName("nursingHomeId")
  private String nursingHomeId = null;

  @SerializedName("nursingServiceId")
  private String nursingServiceId = null;

  @SerializedName("hospital")
  private PatientHospital hospital = null;

  @SerializedName("payer")
  private PatientPayer payer = null;

  @SerializedName("changeInSupplier")
  private Boolean changeInSupplier = null;

  @SerializedName("ivTherapy")
  private Boolean ivTherapy = null;

  @SerializedName("pharmacyId")
  private String pharmacyId = null;

  @SerializedName("comment")
  private String comment = null;

  @SerializedName("billingAddress")
  private PatientBillingAddress billingAddress = null;

  @SerializedName("deliveryAddress")
  private PatientDeliveryAddress deliveryAddress = null;

  @SerializedName("regionId")
  private String regionId = null;

  @SerializedName("fieldNurseId")
  private String fieldNurseId = null;

  @SerializedName("additionalUserId")
  private String additionalUserId = null;

  @SerializedName("classification")
  private String classification = null;

  @SerializedName("deactivationReason")
  private BigDecimal deactivationReason = null;

  @SerializedName("deactivationDate")
  private LocalDate deactivationDate = null;

  @SerializedName("deactivationComment")
  private String deactivationComment = null;

  @SerializedName("careDegree")
  private BigDecimal careDegree = null;

  @SerializedName("careGivers")
  private List<CareGiver> careGivers = null;

  @SerializedName("archived")
  private Boolean archived = null;

  @SerializedName("createdAt")
  private OffsetDateTime createdAt = null;

  @SerializedName("createdBy")
  private String createdBy = null;

  @SerializedName("updatedAt")
  private OffsetDateTime updatedAt = null;

  @SerializedName("updatedBy")
  private String updatedBy = null;

  @SerializedName("timestamp")
  private OffsetDateTime timestamp = null;

  public Patient _id(UUID _id) {
    this._id = _id;
    return this;
  }

   /**
   * Get _id
   * @return _id
  **/
  @Schema(description = "")
  public UUID getId() {
    return _id;
  }

  public void setId(UUID _id) {
    this._id = _id;
  }

  public Patient customerId(String customerId) {
    this.customerId = customerId;
    return this;
  }

   /**
   * Get customerId
   * @return customerId
  **/
  @Schema(example = "D32399", description = "")
  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public Patient customerContactId(String customerContactId) {
    this.customerContactId = customerContactId;
    return this;
  }

   /**
   * Get customerContactId
   * @return customerContactId
  **/
  @Schema(example = "KK123456", description = "")
  public String getCustomerContactId() {
    return customerContactId;
  }

  public void setCustomerContactId(String customerContactId) {
    this.customerContactId = customerContactId;
  }

  public Patient gender(BigDecimal gender) {
    this.gender = gender;
    return this;
  }

   /**
   * 0 &#x3D; Unbekannt, 1 &#x3D; Weiblich, 2 &#x3D; Männlich, 3 &#x3D; Divers
   * minimum: 0
   * maximum: 3
   * @return gender
  **/
  @Schema(example = "1", description = "0 = Unbekannt, 1 = Weiblich, 2 = Männlich, 3 = Divers")
  public BigDecimal getGender() {
    return gender;
  }

  public void setGender(BigDecimal gender) {
    this.gender = gender;
  }

  public Patient title(BigDecimal title) {
    this.title = title;
    return this;
  }

   /**
   * 0 &#x3D; Unbekannt, 1 &#x3D; Dr., 2 &#x3D; Prof. Dr., 3 &#x3D; Dipl. Ing., 4 &#x3D; Dipl. Med., 5 &#x3D; Dipl. Psych., 6 &#x3D; Dr. Dr., 7 &#x3D; Dr. med., 8 &#x3D; Prof. Dr. Dr., 9 &#x3D; Prof., 10 &#x3D; Prof. Dr. med., 11 &#x3D; Rechtsanwalt, 12 &#x3D; Rechtsanwältin, 13 &#x3D; Schwester (Orden)
   * minimum: 0
   * maximum: 13
   * @return title
  **/
  @Schema(example = "1", description = "0 = Unbekannt, 1 = Dr., 2 = Prof. Dr., 3 = Dipl. Ing., 4 = Dipl. Med., 5 = Dipl. Psych., 6 = Dr. Dr., 7 = Dr. med., 8 = Prof. Dr. Dr., 9 = Prof., 10 = Prof. Dr. med., 11 = Rechtsanwalt, 12 = Rechtsanwältin, 13 = Schwester (Orden)")
  public BigDecimal getTitle() {
    return title;
  }

  public void setTitle(BigDecimal title) {
    this.title = title;
  }

  public Patient firstName(String firstName) {
    this.firstName = firstName;
    return this;
  }

   /**
   * Get firstName
   * @return firstName
  **/
  @Schema(example = "Anna", required = true, description = "")
  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public Patient lastName(String lastName) {
    this.lastName = lastName;
    return this;
  }

   /**
   * Get lastName
   * @return lastName
  **/
  @Schema(example = "Mustermann", required = true, description = "")
  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Patient birthday(LocalDate birthday) {
    this.birthday = birthday;
    return this;
  }

   /**
   * Get birthday
   * @return birthday
  **/
  @Schema(example = "Wed Jan 21 00:00:00 GMT 1942", required = true, description = "")
  public LocalDate getBirthday() {
    return birthday;
  }

  public void setBirthday(LocalDate birthday) {
    this.birthday = birthday;
  }

  public Patient address(String address) {
    this.address = address;
    return this;
  }

   /**
   * Get address
   * @return address
  **/
  @Schema(example = "Martin-Richter-Str. 22", description = "")
  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public Patient additionalAddress(String additionalAddress) {
    this.additionalAddress = additionalAddress;
    return this;
  }

   /**
   * Get additionalAddress
   * @return additionalAddress
  **/
  @Schema(example = "Pflegeheim Meier", description = "")
  public String getAdditionalAddress() {
    return additionalAddress;
  }

  public void setAdditionalAddress(String additionalAddress) {
    this.additionalAddress = additionalAddress;
  }

  public Patient additionalAddress2(String additionalAddress2) {
    this.additionalAddress2 = additionalAddress2;
    return this;
  }

   /**
   * Get additionalAddress2
   * @return additionalAddress2
  **/
  @Schema(example = "Zi. 88a", description = "")
  public String getAdditionalAddress2() {
    return additionalAddress2;
  }

  public void setAdditionalAddress2(String additionalAddress2) {
    this.additionalAddress2 = additionalAddress2;
  }

  public Patient postalCode(String postalCode) {
    this.postalCode = postalCode;
    return this;
  }

   /**
   * Get postalCode
   * @return postalCode
  **/
  @Schema(example = "90489", description = "")
  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public Patient city(String city) {
    this.city = city;
    return this;
  }

   /**
   * Get city
   * @return city
  **/
  @Schema(example = "Nürnberg", description = "")
  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Patient phone(String phone) {
    this.phone = phone;
    return this;
  }

   /**
   * Ohne Ländervorwahl
   * @return phone
  **/
  @Schema(example = "0911 3324 323", description = "Ohne Ländervorwahl")
  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Patient fax(String fax) {
    this.fax = fax;
    return this;
  }

   /**
   * Ohne Ländervorwahl
   * @return fax
  **/
  @Schema(example = "0911 3324 323", description = "Ohne Ländervorwahl")
  public String getFax() {
    return fax;
  }

  public void setFax(String fax) {
    this.fax = fax;
  }

  public Patient mobilePhone(String mobilePhone) {
    this.mobilePhone = mobilePhone;
    return this;
  }

   /**
   * Ohne Ländervorwahl
   * @return mobilePhone
  **/
  @Schema(example = "0178 3524 323", description = "Ohne Ländervorwahl")
  public String getMobilePhone() {
    return mobilePhone;
  }

  public void setMobilePhone(String mobilePhone) {
    this.mobilePhone = mobilePhone;
  }

  public Patient email(String email) {
    this.email = email;
    return this;
  }

   /**
   * Get email
   * @return email
  **/
  @Schema(example = "max.mustermann@email.de", description = "")
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Patient primaryDoctorId(String primaryDoctorId) {
    this.primaryDoctorId = primaryDoctorId;
    return this;
  }

   /**
   * Voraussetzung, Alberta-Id ist bereits durch initialen Abgleich in WaWi vorhanden
   * @return primaryDoctorId
  **/
  @Schema(example = "5ab23c369d69c74b68cff5f7", description = "Voraussetzung, Alberta-Id ist bereits durch initialen Abgleich in WaWi vorhanden")
  public String getPrimaryDoctorId() {
    return primaryDoctorId;
  }

  public void setPrimaryDoctorId(String primaryDoctorId) {
    this.primaryDoctorId = primaryDoctorId;
  }

  public Patient primaryDoctorInstitution(PatientPrimaryDoctorInstitution primaryDoctorInstitution) {
    this.primaryDoctorInstitution = primaryDoctorInstitution;
    return this;
  }

   /**
   * Get primaryDoctorInstitution
   * @return primaryDoctorInstitution
  **/
  @Schema(description = "")
  public PatientPrimaryDoctorInstitution getPrimaryDoctorInstitution() {
    return primaryDoctorInstitution;
  }

  public void setPrimaryDoctorInstitution(PatientPrimaryDoctorInstitution primaryDoctorInstitution) {
    this.primaryDoctorInstitution = primaryDoctorInstitution;
  }

  public Patient nursingHomeId(String nursingHomeId) {
    this.nursingHomeId = nursingHomeId;
    return this;
  }

   /**
   * Id des Pflegeheimes (Voraussetzung, Alberta-Id ist bereits durch initialen Abgleich in WaWi vorhanden)
   * @return nursingHomeId
  **/
  @Schema(example = "5ab237059d69c74b68cecbdb", description = "Id des Pflegeheimes (Voraussetzung, Alberta-Id ist bereits durch initialen Abgleich in WaWi vorhanden)")
  public String getNursingHomeId() {
    return nursingHomeId;
  }

  public void setNursingHomeId(String nursingHomeId) {
    this.nursingHomeId = nursingHomeId;
  }

  public Patient nursingServiceId(String nursingServiceId) {
    this.nursingServiceId = nursingServiceId;
    return this;
  }

   /**
   * Id des Pflegedienstes (Voraussetzung, Alberta-Id ist bereits durch initialen Abgleich in WaWi vorhanden)
   * @return nursingServiceId
  **/
  @Schema(example = "5ab2379c9d69c74b68cee819", description = "Id des Pflegedienstes (Voraussetzung, Alberta-Id ist bereits durch initialen Abgleich in WaWi vorhanden)")
  public String getNursingServiceId() {
    return nursingServiceId;
  }

  public void setNursingServiceId(String nursingServiceId) {
    this.nursingServiceId = nursingServiceId;
  }

  public Patient hospital(PatientHospital hospital) {
    this.hospital = hospital;
    return this;
  }

   /**
   * Get hospital
   * @return hospital
  **/
  @Schema(description = "")
  public PatientHospital getHospital() {
    return hospital;
  }

  public void setHospital(PatientHospital hospital) {
    this.hospital = hospital;
  }

  public Patient payer(PatientPayer payer) {
    this.payer = payer;
    return this;
  }

   /**
   * Get payer
   * @return payer
  **/
  @Schema(description = "")
  public PatientPayer getPayer() {
    return payer;
  }

  public void setPayer(PatientPayer payer) {
    this.payer = payer;
  }

  public Patient changeInSupplier(Boolean changeInSupplier) {
    this.changeInSupplier = changeInSupplier;
    return this;
  }

   /**
   * Kennzeichen ob Umversorgungspatient oder nicht
   * @return changeInSupplier
  **/
  @Schema(example = "false", description = "Kennzeichen ob Umversorgungspatient oder nicht")
  public Boolean isChangeInSupplier() {
    return changeInSupplier;
  }

  public void setChangeInSupplier(Boolean changeInSupplier) {
    this.changeInSupplier = changeInSupplier;
  }

  public Patient ivTherapy(Boolean ivTherapy) {
    this.ivTherapy = ivTherapy;
    return this;
  }

   /**
   * Kennzeichen ob IV-Therapie oder nicht
   * @return ivTherapy
  **/
  @Schema(example = "false", description = "Kennzeichen ob IV-Therapie oder nicht")
  public Boolean isIvTherapy() {
    return ivTherapy;
  }

  public void setIvTherapy(Boolean ivTherapy) {
    this.ivTherapy = ivTherapy;
  }

  public Patient pharmacyId(String pharmacyId) {
    this.pharmacyId = pharmacyId;
    return this;
  }

   /**
   * Id der Apotheke (Voraussetzung, Alberta-Id ist bereits durch initialen Abgleich in WaWi vorhanden)
   * @return pharmacyId
  **/
  @Schema(example = "6b945eb5-2f7c-49bb-bd14-d3a11fe385a5", description = "Id der Apotheke (Voraussetzung, Alberta-Id ist bereits durch initialen Abgleich in WaWi vorhanden)")
  public String getPharmacyId() {
    return pharmacyId;
  }

  public void setPharmacyId(String pharmacyId) {
    this.pharmacyId = pharmacyId;
  }

  public Patient comment(String comment) {
    this.comment = comment;
    return this;
  }

   /**
   * Bemerkung zum Patienten
   * @return comment
  **/
  @Schema(example = "true", description = "Bemerkung zum Patienten")
  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public Patient billingAddress(PatientBillingAddress billingAddress) {
    this.billingAddress = billingAddress;
    return this;
  }

   /**
   * Get billingAddress
   * @return billingAddress
  **/
  @Schema(description = "")
  public PatientBillingAddress getBillingAddress() {
    return billingAddress;
  }

  public void setBillingAddress(PatientBillingAddress billingAddress) {
    this.billingAddress = billingAddress;
  }

  public Patient deliveryAddress(PatientDeliveryAddress deliveryAddress) {
    this.deliveryAddress = deliveryAddress;
    return this;
  }

   /**
   * Get deliveryAddress
   * @return deliveryAddress
  **/
  @Schema(description = "")
  public PatientDeliveryAddress getDeliveryAddress() {
    return deliveryAddress;
  }

  public void setDeliveryAddress(PatientDeliveryAddress deliveryAddress) {
    this.deliveryAddress = deliveryAddress;
  }

  public Patient regionId(String regionId) {
    this.regionId = regionId;
    return this;
  }

   /**
   * Id der Region, der der Patient zugeordnet ist (Voraussetzung, Alberta-Id ist bereits durch initialen Abgleich in WaWi vorhanden)
   * @return regionId
  **/
  @Schema(example = "5ab247b49d69c74b68d1debf", required = true, description = "Id der Region, der der Patient zugeordnet ist (Voraussetzung, Alberta-Id ist bereits durch initialen Abgleich in WaWi vorhanden)")
  public String getRegionId() {
    return regionId;
  }

  public void setRegionId(String regionId) {
    this.regionId = regionId;
  }

  public Patient fieldNurseId(String fieldNurseId) {
    this.fieldNurseId = fieldNurseId;
    return this;
  }

   /**
   * Id des zuständingen Außendienstmitarbeiters (Voraussetzung, Alberta-Id ist bereits durch initialen Abgleich in WaWi vorhanden)
   * @return fieldNurseId
  **/
  @Schema(example = "5a6ca8b6456c14307cb9ae35", description = "Id des zuständingen Außendienstmitarbeiters (Voraussetzung, Alberta-Id ist bereits durch initialen Abgleich in WaWi vorhanden)")
  public String getFieldNurseId() {
    return fieldNurseId;
  }

  public void setFieldNurseId(String fieldNurseId) {
    this.fieldNurseId = fieldNurseId;
  }

  public Patient additionalUserId(String additionalUserId) {
    this.additionalUserId = additionalUserId;
    return this;
  }

   /**
   * Id des weiteren (Außendienst-)Mitarbeiters (Voraussetzung, Alberta-Id ist bereits durch initialen Abgleich in WaWi vorhanden)
   * @return additionalUserId
  **/
  @Schema(example = "385958f6-a763-434d-93b1-44417df1baf9", description = "Id des weiteren (Außendienst-)Mitarbeiters (Voraussetzung, Alberta-Id ist bereits durch initialen Abgleich in WaWi vorhanden)")
  public String getAdditionalUserId() {
    return additionalUserId;
  }

  public void setAdditionalUserId(String additionalUserId) {
    this.additionalUserId = additionalUserId;
  }

  public Patient classification(String classification) {
    this.classification = classification;
    return this;
  }

   /**
   * Variabel nutzbares Feld pro Kunde
   * @return classification
  **/
  @Schema(example = "Irgendwas", description = "Variabel nutzbares Feld pro Kunde")
  public String getClassification() {
    return classification;
  }

  public void setClassification(String classification) {
    this.classification = classification;
  }

  public Patient deactivationReason(BigDecimal deactivationReason) {
    this.deactivationReason = deactivationReason;
    return this;
  }

   /**
   * 0 &#x3D; Unbekannt 1 &#x3D; Patient verstorben, 2 &#x3D; Therapieende (alle Therapien), 3 &#x3D; Leistungserbringerwechsel, 4 &#x3D; Sonstiges zusätzlich ab 100 kundenindividuell einstellbare Gründe (nicht hier dokumentiert)
   * @return deactivationReason
  **/
  @Schema(description = "0 = Unbekannt 1 = Patient verstorben, 2 = Therapieende (alle Therapien), 3 = Leistungserbringerwechsel, 4 = Sonstiges zusätzlich ab 100 kundenindividuell einstellbare Gründe (nicht hier dokumentiert)")
  public BigDecimal getDeactivationReason() {
    return deactivationReason;
  }

  public void setDeactivationReason(BigDecimal deactivationReason) {
    this.deactivationReason = deactivationReason;
  }

  public Patient deactivationDate(LocalDate deactivationDate) {
    this.deactivationDate = deactivationDate;
    return this;
  }

   /**
   * Datum der Deaktivierung (ggf. Sterbedatum)
   * @return deactivationDate
  **/
  @Schema(example = "Mon Jan 01 00:00:00 GMT 2018", description = "Datum der Deaktivierung (ggf. Sterbedatum)")
  public LocalDate getDeactivationDate() {
    return deactivationDate;
  }

  public void setDeactivationDate(LocalDate deactivationDate) {
    this.deactivationDate = deactivationDate;
  }

  public Patient deactivationComment(String deactivationComment) {
    this.deactivationComment = deactivationComment;
    return this;
  }

   /**
   * Get deactivationComment
   * @return deactivationComment
  **/
  @Schema(example = "Patient wechselt zu Homecare Plus GmbH", description = "")
  public String getDeactivationComment() {
    return deactivationComment;
  }

  public void setDeactivationComment(String deactivationComment) {
    this.deactivationComment = deactivationComment;
  }

  public Patient careDegree(BigDecimal careDegree) {
    this.careDegree = careDegree;
    return this;
  }

   /**
   * Pflegegrad 1 - 5
   * @return careDegree
  **/
  @Schema(example = "3", description = "Pflegegrad 1 - 5")
  public BigDecimal getCareDegree() {
    return careDegree;
  }

  public void setCareDegree(BigDecimal careDegree) {
    this.careDegree = careDegree;
  }

  public Patient careGivers(List<CareGiver> careGivers) {
    this.careGivers = careGivers;
    return this;
  }

  public Patient addCareGiversItem(CareGiver careGiversItem) {
    if (this.careGivers == null) {
      this.careGivers = new ArrayList<CareGiver>();
    }
    this.careGivers.add(careGiversItem);
    return this;
  }

   /**
   * Get careGivers
   * @return careGivers
  **/
  @Schema(description = "")
  public List<CareGiver> getCareGivers() {
    return careGivers;
  }

  public void setCareGivers(List<CareGiver> careGivers) {
    this.careGivers = careGivers;
  }

  public Patient archived(Boolean archived) {
    this.archived = archived;
    return this;
  }

   /**
   * Kennzeichen ob Patient archiviert ist oder nicht
   * @return archived
  **/
  @Schema(example = "false", description = "Kennzeichen ob Patient archiviert ist oder nicht")
  public Boolean isArchived() {
    return archived;
  }

  public void setArchived(Boolean archived) {
    this.archived = archived;
  }

  public Patient createdAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

   /**
   * Der Zeitstempel der Anlage
   * @return createdAt
  **/
  @Schema(example = "2019-11-28T12:24:41.324Z", description = "Der Zeitstempel der Anlage")
  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public Patient createdBy(String createdBy) {
    this.createdBy = createdBy;
    return this;
  }

   /**
   * Id des anlegenden Benutzers
   * @return createdBy
  **/
  @Schema(example = "b40bae71-b219-45ce-bac6-2d81c2803f39", description = "Id des anlegenden Benutzers")
  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public Patient updatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

   /**
   * Der Zeitstempel der letzten Änderung
   * @return updatedAt
  **/
  @Schema(example = "2019-11-28T12:24:41.324Z", description = "Der Zeitstempel der letzten Änderung")
  public OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Patient updatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
    return this;
  }

   /**
   * Id des ändernden Benutzers
   * @return updatedBy
  **/
  @Schema(example = "b40bae71-b219-45ce-bac6-2d81c2803f39", description = "Id des ändernden Benutzers")
  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public Patient timestamp(OffsetDateTime timestamp) {
    this.timestamp = timestamp;
    return this;
  }

   /**
   * Der letzte Zeitstempel in Alberta
   * @return timestamp
  **/
  @Schema(example = "2019-11-28T12:24:41.324Z", description = "Der letzte Zeitstempel in Alberta")
  public OffsetDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(OffsetDateTime timestamp) {
    this.timestamp = timestamp;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Patient patient = (Patient) o;
    return Objects.equals(this._id, patient._id) &&
        Objects.equals(this.customerId, patient.customerId) &&
        Objects.equals(this.customerContactId, patient.customerContactId) &&
        Objects.equals(this.gender, patient.gender) &&
        Objects.equals(this.title, patient.title) &&
        Objects.equals(this.firstName, patient.firstName) &&
        Objects.equals(this.lastName, patient.lastName) &&
        Objects.equals(this.birthday, patient.birthday) &&
        Objects.equals(this.address, patient.address) &&
        Objects.equals(this.additionalAddress, patient.additionalAddress) &&
        Objects.equals(this.additionalAddress2, patient.additionalAddress2) &&
        Objects.equals(this.postalCode, patient.postalCode) &&
        Objects.equals(this.city, patient.city) &&
        Objects.equals(this.phone, patient.phone) &&
        Objects.equals(this.fax, patient.fax) &&
        Objects.equals(this.mobilePhone, patient.mobilePhone) &&
        Objects.equals(this.email, patient.email) &&
        Objects.equals(this.primaryDoctorId, patient.primaryDoctorId) &&
        Objects.equals(this.primaryDoctorInstitution, patient.primaryDoctorInstitution) &&
        Objects.equals(this.nursingHomeId, patient.nursingHomeId) &&
        Objects.equals(this.nursingServiceId, patient.nursingServiceId) &&
        Objects.equals(this.hospital, patient.hospital) &&
        Objects.equals(this.payer, patient.payer) &&
        Objects.equals(this.changeInSupplier, patient.changeInSupplier) &&
        Objects.equals(this.ivTherapy, patient.ivTherapy) &&
        Objects.equals(this.pharmacyId, patient.pharmacyId) &&
        Objects.equals(this.comment, patient.comment) &&
        Objects.equals(this.billingAddress, patient.billingAddress) &&
        Objects.equals(this.deliveryAddress, patient.deliveryAddress) &&
        Objects.equals(this.regionId, patient.regionId) &&
        Objects.equals(this.fieldNurseId, patient.fieldNurseId) &&
        Objects.equals(this.additionalUserId, patient.additionalUserId) &&
        Objects.equals(this.classification, patient.classification) &&
        Objects.equals(this.deactivationReason, patient.deactivationReason) &&
        Objects.equals(this.deactivationDate, patient.deactivationDate) &&
        Objects.equals(this.deactivationComment, patient.deactivationComment) &&
        Objects.equals(this.careDegree, patient.careDegree) &&
        Objects.equals(this.careGivers, patient.careGivers) &&
        Objects.equals(this.archived, patient.archived) &&
        Objects.equals(this.createdAt, patient.createdAt) &&
        Objects.equals(this.createdBy, patient.createdBy) &&
        Objects.equals(this.updatedAt, patient.updatedAt) &&
        Objects.equals(this.updatedBy, patient.updatedBy) &&
        Objects.equals(this.timestamp, patient.timestamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(_id, customerId, customerContactId, gender, title, firstName, lastName, birthday, address, additionalAddress, additionalAddress2, postalCode, city, phone, fax, mobilePhone, email, primaryDoctorId, primaryDoctorInstitution, nursingHomeId, nursingServiceId, hospital, payer, changeInSupplier, ivTherapy, pharmacyId, comment, billingAddress, deliveryAddress, regionId, fieldNurseId, additionalUserId, classification, deactivationReason, deactivationDate, deactivationComment, careDegree, careGivers, archived, createdAt, createdBy, updatedAt, updatedBy, timestamp);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Patient {\n");
    
    sb.append("    _id: ").append(toIndentedString(_id)).append("\n");
    sb.append("    customerId: ").append(toIndentedString(customerId)).append("\n");
    sb.append("    customerContactId: ").append(toIndentedString(customerContactId)).append("\n");
    sb.append("    gender: ").append(toIndentedString(gender)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    firstName: ").append(toIndentedString(firstName)).append("\n");
    sb.append("    lastName: ").append(toIndentedString(lastName)).append("\n");
    sb.append("    birthday: ").append(toIndentedString(birthday)).append("\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("    additionalAddress: ").append(toIndentedString(additionalAddress)).append("\n");
    sb.append("    additionalAddress2: ").append(toIndentedString(additionalAddress2)).append("\n");
    sb.append("    postalCode: ").append(toIndentedString(postalCode)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("    phone: ").append(toIndentedString(phone)).append("\n");
    sb.append("    fax: ").append(toIndentedString(fax)).append("\n");
    sb.append("    mobilePhone: ").append(toIndentedString(mobilePhone)).append("\n");
    sb.append("    email: ").append(toIndentedString(email)).append("\n");
    sb.append("    primaryDoctorId: ").append(toIndentedString(primaryDoctorId)).append("\n");
    sb.append("    primaryDoctorInstitution: ").append(toIndentedString(primaryDoctorInstitution)).append("\n");
    sb.append("    nursingHomeId: ").append(toIndentedString(nursingHomeId)).append("\n");
    sb.append("    nursingServiceId: ").append(toIndentedString(nursingServiceId)).append("\n");
    sb.append("    hospital: ").append(toIndentedString(hospital)).append("\n");
    sb.append("    payer: ").append(toIndentedString(payer)).append("\n");
    sb.append("    changeInSupplier: ").append(toIndentedString(changeInSupplier)).append("\n");
    sb.append("    ivTherapy: ").append(toIndentedString(ivTherapy)).append("\n");
    sb.append("    pharmacyId: ").append(toIndentedString(pharmacyId)).append("\n");
    sb.append("    comment: ").append(toIndentedString(comment)).append("\n");
    sb.append("    billingAddress: ").append(toIndentedString(billingAddress)).append("\n");
    sb.append("    deliveryAddress: ").append(toIndentedString(deliveryAddress)).append("\n");
    sb.append("    regionId: ").append(toIndentedString(regionId)).append("\n");
    sb.append("    fieldNurseId: ").append(toIndentedString(fieldNurseId)).append("\n");
    sb.append("    additionalUserId: ").append(toIndentedString(additionalUserId)).append("\n");
    sb.append("    classification: ").append(toIndentedString(classification)).append("\n");
    sb.append("    deactivationReason: ").append(toIndentedString(deactivationReason)).append("\n");
    sb.append("    deactivationDate: ").append(toIndentedString(deactivationDate)).append("\n");
    sb.append("    deactivationComment: ").append(toIndentedString(deactivationComment)).append("\n");
    sb.append("    careDegree: ").append(toIndentedString(careDegree)).append("\n");
    sb.append("    careGivers: ").append(toIndentedString(careGivers)).append("\n");
    sb.append("    archived: ").append(toIndentedString(archived)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    createdBy: ").append(toIndentedString(createdBy)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    updatedBy: ").append(toIndentedString(updatedBy)).append("\n");
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
