package icesi.edu.co.mercatero.model

class OrderListInfo{

   var orderInfo: ArrayList<Order> = ArrayList()
   var listName: ArrayList<String> = ArrayList()



    constructor(orderInfo: ArrayList<Order>, listName: ArrayList<String>) {
        this.orderInfo = orderInfo
        this.listName = listName
    }

    constructor()

}