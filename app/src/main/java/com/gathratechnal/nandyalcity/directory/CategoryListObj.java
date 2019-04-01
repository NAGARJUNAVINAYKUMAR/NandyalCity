package com.gathratechnal.nandyalcity.directory;

import java.util.List;
import java.util.Map;

/**
 * Created by arunmididoddy on 12/23/2017.
 */

public class CategoryListObj {

    public List<MainCategory> categoryList;
    public Map<String, List<SubCategory>> SubCategoryMap;

    public List<MainCategory> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<MainCategory> categoryList) {
        this.categoryList = categoryList;
    }

    public Map<String, List<SubCategory>> getSubCategoryMap() {
        return SubCategoryMap;
    }

    public void setSubCategoryMap(Map<String, List<SubCategory>> subCategoryMap) {
        SubCategoryMap = subCategoryMap;
    }




    private class MainCategory {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        private String imgUrl;
    }

    private class SubCategory {
        public String getSubCategoryName() {
            return SubCategoryName;
        }

        public void setSubCategoryName(String subCategoryName) {
            SubCategoryName = subCategoryName;
        }

        private String SubCategoryName;
    }
}
