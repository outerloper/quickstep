package org.quickstep.command;

import javax.swing.*;

import org.quickstep.ComponentFactory;
import org.quickstep.GridBagToolKit;

public class LabelCommand extends AbstractComponentCommand<LabelCommand>
{
   private String text;

   public LabelCommand(String text)
   {
      this.text = text;
   }

   @Override
   public JComponent getComponent(GridBagToolKit.Direction parentDirection, ComponentFactory parentFactory)
   {
      return parentFactory.createLabel(text);
   }
}
