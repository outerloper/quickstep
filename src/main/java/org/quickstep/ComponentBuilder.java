package org.quickstep;

import javax.swing.*;

public interface ComponentBuilder
{
   GridBagSpec getSpec();

   JComponent build();
}
