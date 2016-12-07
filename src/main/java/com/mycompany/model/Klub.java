package com.mycompany.model;

import java.sql.Blob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.Hibernate;
import org.hibernate.Session;

@Entity
@Table(name = "klub")
public class Klub implements Serializable {

    //<img th:src="${(comment.user.image != null && !#strings.isEmpty(comment.user.image)) ? comment.user.image : '/images/default-user.png'}" th:alt="${comment.user.nameSurname}"/>
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Klub")
    private Integer idKlub;
	
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "nazwa")
    private String nazwa;
    
    @Lob
    @Column(name = "logo")
    private Blob logo;
	
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idKlub")
    private Collection<CzlonekZarzadu> czlonekZarzaduCollection;
	
    @OneToMany(mappedBy = "idKlub")
    private Collection<Budynek> budynekCollection;
	
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idKlub")
    private Collection<Sekcja> sekcjaCollection;

    public Klub() {
    }

    public Klub(Integer idKlub) {
        this.idKlub = idKlub;
    }

    public Klub(Integer idKlub, String nazwa) {
        this.idKlub = idKlub;
        this.nazwa = nazwa;
    }

    public Integer getIdKlub() {
        return idKlub;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Blob getLogo() {
        return logo;
    }
    
    public byte[] getByteLogo() throws SQLException{
        return logo.getBytes(1, (int)logo.length());
    }

    public void setLogo(Blob logo) {
        this.logo = logo;
    }
    
    public void setLogo(String imagePath, Session session) throws FileNotFoundException, SQLException, IOException {
        File file = new File(imagePath);
        FileInputStream inputStream = new FileInputStream(file);
        Blob blob = (Blob) Hibernate.getLobCreator(session).createBlob(inputStream,file.length());
        this.logo = blob;
//        blob.free();
//        inputStream.close();
    }

    public Collection<CzlonekZarzadu> getCzlonekZarzaduCollection() {
        return czlonekZarzaduCollection;
    }

    public void setCzlonekZarzaduCollection(Collection<CzlonekZarzadu> czlonekZarzaduCollection) {
        this.czlonekZarzaduCollection = czlonekZarzaduCollection;
    }

    public Collection<Budynek> getBudynekCollection() {
        return budynekCollection;
    }

    public void setBudynekCollection(Collection<Budynek> budynekCollection) {
        this.budynekCollection = budynekCollection;
    }

    public Collection<Sekcja> getSekcjaCollection() {
        return sekcjaCollection;
    }

    public void setSekcjaCollection(Collection<Sekcja> sekcjaCollection) {
        this.sekcjaCollection = sekcjaCollection;
    }
    
}
