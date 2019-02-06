package com.visualstore.Model;

public class ProductLensDataModel {
    private String id;
    private String lens_type;
    private String name;
    private String edi_code;
    private String lens_code;
    private String available_coatings;
    private String individual;
    private String addition;
    private String tint;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLens_type() {
        return lens_type;
    }

    public void setLens_type(String lens_type) {
        this.lens_type = lens_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEdi_code() {
        return edi_code;
    }

    public void setEdi_code(String edi_code) {
        this.edi_code = edi_code;
    }

    public String getLens_code() {
        return lens_code;
    }

    public void setLens_code(String lens_code) {
        this.lens_code = lens_code;
    }

    public String getAvailable_coatings() {
        return available_coatings;
    }

    public void setAvailable_coatings(String available_coatings) {
        this.available_coatings = available_coatings;
    }

    public String getIndividual() {
        return individual;
    }

    public void setIndividual(String individual) {
        this.individual = individual;
    }

    public String getAddition() {
        return addition;
    }

    public void setAddition(String addition) {
        this.addition = addition;
    }

    public String getTint() {
        return tint;
    }

    public void setTint(String tint) {
        this.tint = tint;
    }
}
