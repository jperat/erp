/*
 * Copyright 2015 Jerome PERAT.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.erp.beans;

import java.util.ArrayList;

public class Project {
    private Long   id;
    private String name;
    private String description;
    private ArrayList<ProjectProduct> projectProducts;
    
    public Project() {
        this.projectProducts = new ArrayList<ProjectProduct>();
    }

    public Long getId() {
        return id;
    }

    public void setId( Long id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public ArrayList<ProjectProduct> getProjectProducts() {
        return projectProducts;
    }

    public void setProjectProducts(ArrayList<ProjectProduct> projectProducts) {
        this.projectProducts = projectProducts;
    }
    
    public void addProjectProduct(ProjectProduct projectProduct) {
        this.projectProducts.add(projectProduct);
    }

}
