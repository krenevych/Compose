package com.example.di

import com.example.di.computer.Computer
import com.example.di.computer.Memory
import com.example.di.computer.Processor
import com.example.di.computer.Ssd

class Component {

    fun inject(activity: MainActivity) {
        activity.computer = Component().getComputer()
    }

    private fun getComputer(): Computer
    {
        val processor = Processor()
        val memory = Memory()
        val storage = Ssd()

        return Computer(processor, memory, storage)
    }

}