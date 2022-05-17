package com.example.appshitaroundtheworld.model
import kotlinx.serialization.Serializable

@Serializable
class Land (val naam:String, val rating: Double, val comment:String, val jaar:Int, val maand:Int, val dag:Int, val latitude: Double, val longitude: Double, val eersteKeer:Boolean): java.io.Serializable{
}