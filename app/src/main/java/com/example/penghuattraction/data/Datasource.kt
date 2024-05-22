package com.example.penghuattraction.data

import com.example.penghuattraction.R
import com.example.penghuattraction.model.Attraction

class Datasource {
    fun loadAttractions(): List<Attraction>{
        return listOf<Attraction>(
            Attraction(1,R.string.attraction1, R.drawable.jibei,R.string.description1,23.727919,119.6031607),
            Attraction(2,R.string.attraction2,R.drawable.twinheart,R.string.description2,23.220255,119.4469133),
            Attraction(3,R.string.attraction3,R.drawable._kan,R.string.description3,23.605174,119.519615),
            Attraction(4,R.string.attraction4,R.drawable.oldstreet,R.string.description4,23.5647548,119.5648526),
            Attraction(5,R.string.attraction5,R.drawable.stone,R.string.description5,23.595584,119.5163386),
            Attraction(6,R.string.attraction6,R.drawable.guanyinting,R.string.description6,23.5686457,119.562431),
            Attraction(7,R.string.attraction7,R.drawable.duxin,R.string.description7,23.5638542,119.5609494),
            Attraction(8,R.string.attraction8,R.drawable.museum,R.string.description8,23.5669727,119.5794457),
            Attraction(9,R.string.attraction9,R.drawable.bridge,R.string.description9,23.6430519,119.542231),
            Attraction(10,R.string.attraction10,R.drawable.imenbeach,R.string.description10,23.5548761,119.6390837)
        )
    }
}