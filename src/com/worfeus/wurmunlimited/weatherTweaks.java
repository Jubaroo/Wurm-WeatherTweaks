package com.worfeus.wurmunlimited;

/**
 * Created by Wim on 1/27/2018.
 */

import com.wurmonline.server.WurmCalendar;
import com.wurmonline.server.creatures.Communicator;
import com.wurmonline.server.players.Player;

import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
//import org.gotti.wurmunlimited.modloader.classhooks.HookManager;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;
import org.gotti.wurmunlimited.modloader.interfaces.Configurable;
import org.gotti.wurmunlimited.modloader.interfaces.Initable;
import org.gotti.wurmunlimited.modloader.interfaces.PreInitable;
import org.gotti.wurmunlimited.modloader.interfaces.WurmServerMod;

import org.gotti.wurmunlimited.modloader.interfaces.PlayerMessageListener;

public class weatherTweaks implements WurmServerMod, Initable, PreInitable, Configurable,PlayerMessageListener{

    private static final Logger logger = Logger.getLogger("weatherTweaks");

    public weatherTweaks(){

    }

    public void configure(Properties properties) {

    }

    public boolean onPlayerMessage(Communicator communicator, String msg) {
        if(!msg.startsWith("/myweather")) {
            return false;
        } else {
            String commandMessage = Communicator.getCommandMessage();
            Player player = communicator.player;
            String message = "";

            int day = (int)(WurmCalendar.currentTime % 29030400L / 86400L);
            double starfall = (double)WurmCalendar.getStarfall() + (double)day % 28.0D / 28.0D;
            int starfallWeek = WurmCalendar.getStarfallWeek();
            int daysToNextSeason = 0;
            String nextSeason = "";

            // Spring
            if (starfallWeek >= 2 && starfallWeek < 12){
                nextSeason = "Summer";
                daysToNextSeason = 87-day;
            }

            //Summer
            if (starfallWeek >= 12 && starfallWeek < 35){
                nextSeason = "Autumn";
                daysToNextSeason = 248-day;
            }

            // Autumn
            if (starfallWeek >= 35 && starfallWeek < 45){
                nextSeason = "Winter";
                daysToNextSeason = 318-day;
            }

            // Winter
            if (starfallWeek >= 46 || starfallWeek < 2){
                nextSeason = "Spring";
                if (day >= 318) {
                    daysToNextSeason = 357-day;
                }
                else {
                    daysToNextSeason = 21-day;
                }

            }

            message = "There are "+daysToNextSeason+ " days until "+ nextSeason;

            player.getCommunicator().sendNormalServerMessage(message);
        }
        return true;
    }

    public void preInit(){



    }

    public void init(){


    }


    public static void logException(String msg, Throwable e) {
        if(logger != null) {
            logger.log(Level.SEVERE, msg, e);
        }

    }

    public static void logWarning(String msg) {
        if(logger != null) {
            logger.log(Level.WARNING, msg);
        }

    }

    public static void logInfo(String msg) {
        if(logger != null) {
            logger.log(Level.INFO, msg);
        }

    }

}