package com.example.Guidetothegalaxy

import java.io.File

class Main {
    fun parseToResult(lines: MutableList<String>,
                      productToCredits: MutableMap<String, Float>,
                      nameToSymbol: MutableMap<String, String>,
                      mapOfSymbol: Map<String, Int>): MutableList<String> {
        val result = mutableListOf<String>()
        for (line in lines) {
            val splitedLine = line.split(" ")
            val lastIndexValue = splitedLine[splitedLine.lastIndex]
            val indexOfIs = splitedLine.indexOf("is")
            if (indexOfIs < 0) {
                result.add("I have no idea what you are talking about")
                continue
            }
            if (mapOfSymbol.containsKey(lastIndexValue)) {
                nameToSymbol.put(splitedLine[0], splitedLine[2])
            } else if (lastIndexValue == "Credits") {
                val nameList = splitedLine.subList(0, indexOfIs - 1)
                val parseToSymbol = parseToSymbol(nameList, nameToSymbol)
                val parseToSum = parseToSum(parseToSymbol, mapOfSymbol)
                productToCredits[splitedLine[indexOfIs - 1]] = splitedLine[indexOfIs + 1].toFloat() / parseToSum
            } else if (splitedLine[1] == "much") {
                val nameList = splitedLine.subList(indexOfIs + 1, splitedLine.lastIndex)
                val parseToSymbol = parseToSymbol(nameList, nameToSymbol)
                if (parseToSymbol[0] == "I have no idea what you are talking about") {
                    result.add(parseToSymbol[0])
                    continue
                }
                val parseToSum = parseToSum(parseToSymbol, mapOfSymbol)
                val namesString = nameList.joinToString(" ")
                result.add("$namesString is ${parseToSum.toInt()}")
            } else if (splitedLine[1] == "many") {
                val nameList = splitedLine.subList(indexOfIs + 1, splitedLine.lastIndex - 1)
                val product = splitedLine[splitedLine.lastIndex - 1]
                val parseToSymbol = parseToSymbol(nameList, nameToSymbol)
                val unitProductCredits = productToCredits[product]
                if (parseToSymbol[0] == "I have no idea what you are talking about") {
                    result.add(parseToSymbol[0])
                    continue
                }
                val parseToSum = parseToSum(parseToSymbol, mapOfSymbol)
                val namesString = nameList.joinToString(" ")
                result.add("$namesString ${product} is ${(parseToSum * unitProductCredits!!).toInt()} Credits")
            }
        }
        return result
    }

    fun parseToSymbol(nameList: List<String>, nameToSymbol: MutableMap<String, String>): MutableList<String> {
        val result = mutableListOf<String>()
        for (name in nameList) {
            val symbol = nameToSymbol[name]
            if (symbol == null) {
                result.add("I have no idea what you are talking about")
                return result
            }
            result.add(symbol)
        }
        return result
    }

    fun parseToSum(symbolList: MutableList<String>, mapOfSymbol: Map<String, Int>): Int {
        var sum: Int = 0
        val isLegalFormat: Boolean = checkSymbol(symbolList)
        if (isLegalFormat) {
            while (symbolList.size > 1) {
                val lastSymbol = symbolList[0]
                val currentSymbol = symbolList[1]
                val lastNumber = mapOfSymbol[lastSymbol]
                val currentNumber = mapOfSymbol[currentSymbol]
                if (currentNumber!! > lastNumber!!) {
                    sum += currentNumber - lastNumber
                    symbolList.removeAt(0)
                    symbolList.removeAt(0)
                } else {
                    sum += lastNumber
                    symbolList.removeAt(0)
                }
            }
            if (symbolList.size > 0) {
                for (symbol in symbolList) {
                    sum += mapOfSymbol.getValue(symbol)
                }
            }
        }
        return sum
    }

    val limitedSymbol = listOf<String>("I", "X", "C", "M")
    val noRepeatedSymbol = listOf<String>("D", "L", "V")
    val canBeBehindI = listOf<String>("I", "V", "X")
    val canBeBehindX = listOf<String>("X", "L", "C")
    val canBeBehindC = listOf<String>("C", "D", "M")
    val canBeBehindV = listOf<String>("V", "I")
    val canBeBehindL = listOf<String>("V", "I", "X", "L")
    val canBeBehindD = listOf<String>("V", "I", "X", "L", "D", "C")
    fun checkSymbol(symbolList: MutableList<String>): Boolean {
        val groupSymbol = groupSymbol(symbolList)
        for (symbol in limitedSymbol) {
            if (groupSymbol[symbol] != null && groupSymbol[symbol]!! > 3) {
                return false
            }
        }
        for (symbol in noRepeatedSymbol) {
            if (groupSymbol[symbol] != null && groupSymbol[symbol]!! > 1) {
                return false
            }
        }
        for (index in 0..symbolList.size - 2) {
            if (symbolList[index] == "I" && !canBeBehindI.contains(symbolList[index + 1])) {
                return false
            } else if (symbolList[index] == "X" && !canBeBehindX.contains(symbolList[index + 1])) {
                return false
            } else if (symbolList[index] == "C" && !canBeBehindC.contains(symbolList[index + 1])) {
                return false
            } else if (symbolList[index] == "V" && !canBeBehindV.contains(symbolList[index + 1])) {
                return false
            } else if (symbolList[index] == "L" && !canBeBehindL.contains(symbolList[index + 1])) {
                return false
            } else if (symbolList[index] == "D" && !canBeBehindD.contains(symbolList[index + 1])) {
                return false
            }
        }

        return true
    }

    fun groupSymbol(symbolList: MutableList<String>): MutableMap<String, Int> {
        val numberOfSymbol = mutableMapOf<String, Int>()
        for (symbol in symbolList) {
            val count = numberOfSymbol[symbol]
            if (count == null) numberOfSymbol[symbol] = 1
            else numberOfSymbol[symbol] = count + 1
        }
        return numberOfSymbol
    }

}