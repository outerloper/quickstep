package org.quickstep;

import javax.swing.*;

public class PanelCommand extends GridContainerCommand<JPanel, PanelCommand>
{
   protected PanelCommand()
   {
   }

   @Override
   protected JPanel createDefaultContainer(ComponentFactory factory)
   {
      return factory.createPanel();
   }
}
