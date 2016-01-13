/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.egen.san.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import org.apache.commons.lang.ObjectUtils;

/**
 *
 * @author san
 */
@Entity
@Table(name="USERS")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    @NotNull(message = "id cannot be null for User")
    private String id;
    
    @Column(name = "FIRST_NAME", nullable = false)
    @NotNull(message = "firstName cannot be null for User")
    private String firstName;
    
    @Column(name = "MIDDLE_NAME", nullable = true)
    private String middleName;
    
    @Column(name = "LAST_NAME", nullable = false)
    @NotNull(message = "lastName cannot be null for User")
    private String lastName;
    
    @Column(name = "AGE", nullable = false)
    @NotNull(message = "age cannot be null for User")
    @Min(value = 1, message = "Age should atleast be 1")
    private Short age;
    
    @Column(name = "GENDER", nullable = false)
    @NotNull(message = "gender cannot be null for User")
    private Character gender;
    
    @Column(name = "PHONE", nullable = false)
    @NotNull(message = "phone number cannot be null for User")
    @Max(value = 9999999999L, message = "Phone number cannot be more than 10 digits")
    private Long phone;
    
    @Column(name = "ZIP", nullable = true)
    private String zip;

    public User() {
    }

    public User(String _id, String _firstName, String _middleName, String _lastName, Short _age, Character _gender, Long _phone, String _zip) {
        this.id = _id;
        this.firstName = _firstName;
        this.middleName = _middleName;
        this.lastName = _lastName;
        this.age = _age;
        this.gender = _gender;
        this.phone = _phone;
        this.zip = _zip;
    }
    
    public User(String _id, String _firstName, String _lastName, Short _age, Character _gender, Long _phone) {
        this(_id, _firstName, null, _lastName, _age, _gender, _phone, null);
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String _firstName) {
        this.firstName = _firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String _middleName) {
        this.middleName = _middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String _lastName) {
        this.lastName = _lastName;
    }

    public Short getAge() {
        return age;
    }

    public void setAge(Short _age) {
        this.age = _age;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character _gender) {
        this.gender = _gender;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long _phone) {
        this.phone = _phone;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String _zip) {
        this.zip = _zip;
    }
    
    public static User copyUserData(User to, User from){
        if(from.getFirstName() != null && !from.getFirstName().isEmpty()){
            to.setFirstName(from.getFirstName());
        }
        
        if(from.getLastName() != null && !from.getLastName().isEmpty()){
            to.setLastName(from.getLastName());
        }
        
        if(from.getMiddleName() != null && !from.getMiddleName().isEmpty()){
            to.setMiddleName(from.getMiddleName());
        }
        
        if(from.getZip() != null && !from.getZip().isEmpty()){
            to.setZip(from.getZip());
        }
        
        if(from.getAge() != null && from.getAge() > 0){
            to.setAge(from.getAge());
        }
        
        if(from.getPhone() != null && from.getPhone() > 0){
            to.setPhone(from.getPhone());
        }
        
        if(from.getGender() != null){
            to.setGender(from.getGender());
        }
        
        return to;
    }
    
    /**
     * Returns true if all the values of both user objects are equal. Null safe
     * @param o1
     * @param o2
     * @return 
     */
    public static boolean isAllValueEquals(User o1, User o2){
        if(o1 == null || o2 == null){
            return o1 == o2;
        }
        
        if(ObjectUtils.compare(o1.id, o2.id) != 0)
            return false;
        
        if(ObjectUtils.compare(o1.firstName, o2.firstName) != 0)
            return false;
        
        if(ObjectUtils.compare(o1.middleName, o2.middleName) != 0)
            return false;
        
        if(ObjectUtils.compare(o1.lastName, o2.lastName) != 0)
            return false;
        
        if(ObjectUtils.compare(o1.age, o2.age) != 0)
            return false;
        
        if(ObjectUtils.compare(o1.gender, o2.gender) != 0)
            return false;
        
        if(ObjectUtils.compare(o1.phone, o2.phone) != 0)
            return false;
        
        if(ObjectUtils.compare(o1.zip, o2.zip) != 0)
            return false;
        
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.egen.san.model.User[ id=" + id + " ]";
    }
    
}
