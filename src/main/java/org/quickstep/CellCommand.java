package org.quickstep;

import javax.swing.*;

public interface CellCommand extends GridBagCommand
{
   JComponent getComponent();

   GridBagSpec getSpec();
}