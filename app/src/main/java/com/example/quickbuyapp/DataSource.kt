package com.example.quickbuyapp

import com.example.quickbuyapp.model.ItemToDisplay

class DataSource{

    companion object{

        fun createDataSet(): ArrayList<ItemToDisplay>{
            val list = ArrayList<ItemToDisplay>()
            list.add(
                ItemToDisplay(
                    "Buy Utility Items",
                    "https://cdn.pixabay.com/photo/2017/04/06/11/24/fashion-2208045_960_720.jpg",
                    "https://cdn.pixabay.com/photo/2016/03/27/22/16/fashion-1284496_960_720.jpg",
                    "https://cdn.pixabay.com/photo/2014/01/22/19/38/boot-250012_960_720.jpg",
                    "https://cdn.pixabay.com/photo/2017/02/08/02/56/booties-2047596_960_720.jpg"
                )
            )
            list.add(
                ItemToDisplay(
                    "Shoes For all",
                    "https://cdn.pixabay.com/photo/2014/01/22/19/38/boot-250012_960_720.jpg",
                    "https://cdn.pixabay.com/photo/2017/02/08/02/56/booties-2047596_960_720.jpg",
                    "https://cdn.pixabay.com/photo/2017/04/06/11/24/fashion-2208045_960_720.jpg",
                    "https://cdn.pixabay.com/photo/2016/03/27/22/16/fashion-1284496_960_720.jpg"

                )
            )

            list.add(
                ItemToDisplay(
                    "General Items",
                    "https://cdn.pixabay.com/photo/2017/10/11/14/32/blur-2841225_960_720.jpg",
                    "https://cdn.pixabay.com/photo/2014/08/26/21/49/jeans-428614_960_720.jpg",
                    "https://cdn.pixabay.com/photo/2014/07/27/17/29/ironing-403074_960_720.jpg",
                    "https://cdn.pixabay.com/photo/2018/03/27/17/50/kitchen-3266752_960_720.jpg"
                )
            )
            return list
        }
    }
}
