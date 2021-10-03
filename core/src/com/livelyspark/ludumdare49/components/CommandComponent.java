package com.livelyspark.ludumdare49.components;

import com.badlogic.ashley.core.Component;
import com.livelyspark.ludumdare49.enums.Commands;
import com.livelyspark.ludumdare49.enums.Effects;

public class CommandComponent implements Component {

    public Commands command;

    public CommandComponent(Commands command)
    {
        this.command = command;
    }

}
