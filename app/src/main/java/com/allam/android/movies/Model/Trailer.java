package com.allam.android.movies.Model;

import java.util.List;

/**
 * Created by Allam on 06/09/2016.
 */
public class Trailer {

    /**
     * id : 5794ccaa9251414236001173
     * iso_639_1 : en
     * iso_3166_1 : US
     * key : 43NWzay3W4s
     * name : Official Trailer #1
     * site : YouTube
     * size : 1080
     * type : Trailer
     */

    private List<ResultsBean> results;

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        private String key;
        private String name;
        private int size;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }
    }
}