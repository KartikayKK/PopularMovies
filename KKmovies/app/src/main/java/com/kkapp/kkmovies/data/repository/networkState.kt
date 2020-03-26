package com.kkapp.kkmovies.data.repository
enum class status{
    RUNNING
    ,SUCCESS
    ,FAILED
}
class networkState(val status: status,val msg:String) {

    companion object{
        val LOADED:networkState
        val LOADING:networkState
        val ERROR:networkState
        val ENDOFLIST:networkState


        init {
            LOADED= networkState(status.SUCCESS,msg ="Success" )
            LOADING= networkState(status.RUNNING,msg ="Running" )
            ERROR= networkState(status.FAILED,msg ="Something Went Wrong" )
            ENDOFLIST= networkState(status.FAILED,msg ="You have reached the end of list" )



        }
    }
}