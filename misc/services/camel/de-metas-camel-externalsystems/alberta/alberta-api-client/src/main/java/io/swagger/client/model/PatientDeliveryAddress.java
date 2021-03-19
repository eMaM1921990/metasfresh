/*
 * Patient - Warenwirtschaft (Basis)
 * Synchronisation der Patienten mit der Warenwirtschaft
 *
 * OpenAPI spec version: 1.0.6
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
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.IOException;
import java.math.BigDecimal;
/**
 * generell ABWEICHENDE Lieferadresse
 */
@Schema(description = "generell ABWEICHENDE Lieferadresse")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaClientCodegen", date = "2021-02-11T10:30:25.848Z[GMT]")
public class PatientDeliveryAddress {
  @SerializedName("gender")
  private BigDecimal gender = null;

  @SerializedName("title")
  private BigDecimal title = null;

  @SerializedName("name")
  private String name = null;

  @SerializedName("address")
  private String address = null;

  @SerializedName("postalCode")
  private String postalCode = null;

  @SerializedName("city")
  private String city = null;

  public PatientDeliveryAddress gender(BigDecimal gender) {
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

  public PatientDeliveryAddress title(BigDecimal title) {
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

  public PatientDeliveryAddress name(String name) {
    this.name = name;
    return this;
  }

   /**
   * Get name
   * @return name
  **/
  @Schema(example = "Susanne Mustermann", description = "")
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public PatientDeliveryAddress address(String address) {
    this.address = address;
    return this;
  }

   /**
   * Get address
   * @return address
  **/
  @Schema(example = "Lange Gasse 33", description = "")
  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public PatientDeliveryAddress postalCode(String postalCode) {
    this.postalCode = postalCode;
    return this;
  }

   /**
   * Get postalCode
   * @return postalCode
  **/
  @Schema(example = "90491", description = "")
  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public PatientDeliveryAddress city(String city) {
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


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PatientDeliveryAddress patientDeliveryAddress = (PatientDeliveryAddress) o;
    return Objects.equals(this.gender, patientDeliveryAddress.gender) &&
        Objects.equals(this.title, patientDeliveryAddress.title) &&
        Objects.equals(this.name, patientDeliveryAddress.name) &&
        Objects.equals(this.address, patientDeliveryAddress.address) &&
        Objects.equals(this.postalCode, patientDeliveryAddress.postalCode) &&
        Objects.equals(this.city, patientDeliveryAddress.city);
  }

  @Override
  public int hashCode() {
    return Objects.hash(gender, title, name, address, postalCode, city);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PatientDeliveryAddress {\n");
    
    sb.append("    gender: ").append(toIndentedString(gender)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    name: ").append(toIndentedString(name)).append("\n");
    sb.append("    address: ").append(toIndentedString(address)).append("\n");
    sb.append("    postalCode: ").append(toIndentedString(postalCode)).append("\n");
    sb.append("    city: ").append(toIndentedString(city)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}
