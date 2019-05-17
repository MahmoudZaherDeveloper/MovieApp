package com.mahmoud.zaher.intcoretask_movieapp_.movieModel;

import com.mahmoud.zaher.intcoretask_movieapp_.movieModel.apiResponse.Result;

import java.io.Serializable;
import java.util.List;


public class ViewModel implements Serializable {

    private List<Result> moviesResults;

    public ViewModel(List<Result> moviesResults) {
        this.moviesResults = moviesResults;
    }

    public List<Result> getMoviesResults() {
        return moviesResults;
    }

    public void setMoviesResults(List<Result> moviesResults) {
        this.moviesResults = moviesResults;
    }
}
