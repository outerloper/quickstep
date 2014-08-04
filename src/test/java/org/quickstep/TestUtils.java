package org.quickstep;

import java.awt.*;
import javax.swing.*;

import org.easymock.IArgumentMatcher;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.reportMatcher;
import static org.quickstep.GridBagToolKit.*;
import static org.quickstep.util.DebugSupport.gbcEquals;
import static org.quickstep.util.DebugSupport.gbcToString;

public class TestUtils
{
   static class GBCMatcher implements IArgumentMatcher
   {
      GridBagConstraints constraints;

      GBCMatcher(GridBagConstraints constraints)
      {
         this.constraints = constraints;
      }

      @Override
      public boolean matches(Object o)
      {
         return o instanceof GridBagConstraints && gbcEquals(constraints, (GridBagConstraints) o);
      }

      @Override
      public void appendTo(StringBuffer stringBuffer)
      {
         stringBuffer.append(gbcToString(constraints));
      }
   }

   static GBCMatcher gbc(int x, int y, CellSpec spec)
   {
      reportMatcher(new GBCMatcher(spec.toConstraints(x, y)));
      return null;
   }

   static GridBagConstraints anyGbc()
   {
      return anyObject(GridBagConstraints.class);
   }

   static JComponent anyComponent()
   {
      return anyObject();
   }

   static JLabel aComponent()
   {
      return new JLabel();
   }

   static CellSpec defaultSpec()
   {
      return spec().withAnchorX(AX.LEFT);
   }
}
