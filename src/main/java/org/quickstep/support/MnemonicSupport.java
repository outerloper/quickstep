package org.quickstep.support;


import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.*;

public class MnemonicSupport
{
   public static final String MNEMONIC_STRING = "&(.)";
   public static final Pattern MNEMONIC_PATTERN = Pattern.compile(MNEMONIC_STRING);
   public static final int NULL_CHAR = 0;

   private final char mnemonic;
   private final String text;
   private final int index;

   public static boolean setUp(JButton button)
   {
      MnemonicSupport support = new MnemonicSupport(button.getText());
      button.setText(support.getText());
      if (support.hasMnemonic())
      {
         button.setMnemonic(support.getMnemonic());
         button.setDisplayedMnemonicIndex(support.getIndex());
         return true;
      }
      return false;
   }

   public static boolean setUp(JLabel label, Component labelFor)
   {
      MnemonicSupport support = new MnemonicSupport(label.getText());
      label.setText(support.getText());
      if (support.hasMnemonic())
      {
         label.setDisplayedMnemonic(support.getMnemonic());
         label.setDisplayedMnemonicIndex(support.getIndex());
         label.setLabelFor(labelFor);
         return true;
      }
      return false;
   }

   public MnemonicSupport(String textWithMnemonic)
   {
      if (textWithMnemonic != null)
      {
         Matcher matcher = MNEMONIC_PATTERN.matcher(textWithMnemonic);
         int indexOffset = 0;
         while (matcher.find())
         {
            indexOffset++;
            if (matcher.group(1).charAt(0) != '&')
            {
               mnemonic = matcher.group(1).charAt(0);
               index = matcher.start(1) - indexOffset;
               text = textWithMnemonic.replaceAll(MNEMONIC_STRING, "$1");
               return;
            }
         }
         text = textWithMnemonic.replaceAll(MNEMONIC_STRING, "$1");
      }
      else
      {
         text = null;
      }
      mnemonic = NULL_CHAR;
      index = -1;
   }

   public boolean hasMnemonic()
   {
      return mnemonic != NULL_CHAR;
   }

   public char getMnemonic()
   {
      return mnemonic;
   }

   public int getIndex()
   {
      return index;
   }

   public String getText()
   {
      return text;
   }
}