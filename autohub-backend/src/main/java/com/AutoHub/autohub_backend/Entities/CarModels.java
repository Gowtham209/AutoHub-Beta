package com.AutoHub.autohub_backend.Entities;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Data
@Table(
        uniqueConstraints = {@UniqueConstraint(columnNames = {"modelName"})}
)
public class CarModels {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long modelId;
    @ManyToOne(
            cascade = CascadeType.PERSIST,
            optional = true
    )
    @JoinColumn(
            name = "category_id",
            referencedColumnName = "categoryId"
    )
    private Category category;
//   @JsonBackReference(value="categoryOfEachCar")
    
    
    @ManyToMany(
            mappedBy = "favoriteCars",
            fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST
    )
    @JsonIgnore
    private List<Users> user=new LinkedList<>();
    private Double price;
    private Integer year;
    private Integer carLots;
    private String modelName;
    private Boolean available;
    private Boolean isVisible;
	
   	public Boolean getIsVisible() {
   		return isVisible;
   	}
   	public void setIsVisible(Boolean isVisible) {
   		this.isVisible = isVisible;
   	}

    public List<Users> getUser() {
        return user;
    }

    public void setUser(List<Users> user) {
        this.user = user;
    }
   
    
    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
    
 

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Integer getCarLots() {
        return carLots;
    }

    public void setCarLots(Integer carLots) {
        this.carLots = carLots;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString()
    {
        return "[ "+modelId+" , "+modelName+" ]"+"\nCategory:"+category;
    }

}
