package fr.bowserf.cmakesample

class Calculator {

    external fun multiply(value1: Long, value2: Long): Long

    companion object {
        init {
            System.loadLibrary("calculator")
        }
    }

}