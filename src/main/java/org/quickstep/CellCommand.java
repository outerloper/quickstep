package org.quickstep;

import javax.swing.*;

public interface CellCommand<T extends CellCommand<T>> extends GridBagCommand
{
   JComponent getComponent();

   CellSpec getSpec();

   T withSpec(CellSpec spec);
}
