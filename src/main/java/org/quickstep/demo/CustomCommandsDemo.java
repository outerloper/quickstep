package org.quickstep.demo;

import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;

import org.quickstep.*;

import static javax.swing.UIManager.*;
import static org.quickstep.GridBagToolKit.*;

public class CustomCommandsDemo extends JFrame
{
   public CustomCommandsDemo()
   {
      JPanel explicitlyProvidedPanel = new JPanel();
      explicitlyProvidedPanel.setBackground(Color.GREEN);

      buildContent(this, panel().
         withContentAnchor(A.BOTH).
         withOrientation(Orientation.VERTICAL).
         add(toolbar().
            add(new JButton(getIcon("FileChooser.newFolderIcon"))).
            add(new JButton(getIcon("FileChooser.upFolderIcon"))).
            addSeparator().
            add(new JButton(getIcon("FileChooser.detailsViewIcon"))).
            add(new JButton("Details..."))
         ).
         add(tabs().
            add(tab("First").add("First tab content")).
            add(tab("Second").
               withContentAnchor(A.BOTH).
               add(split(Orientation.VERTICAL).
                  withResizeWeight(0.67).
                  withFirst(panel().add("Third tab top content")).
                  withSecond(panel().add("Third tab bottom content"))
               )
            ).
            add(tab("Third").
               withContentAnchor(A.BOTH).
               add(split(Orientation.HORIZONTAL).
                  withFirst(panel().add("Third tab left content")).
                  withSecond(panel().add("Third tab right content"))
               )
            ).
            add(tab("Fourth").
               on(explicitlyProvidedPanel).
               add("Fourth tab content")
            ).
            add(tab("Details").
               withIcon(getIcon("FileChooser.detailsViewIcon")).
               add("<html><i>Details</i> tab content</html>")
            )
         ).
         add(buttons().
            add(new JButton("OK")).
            add(new JButton("Cancel")).
            add(new JButton("Help"))
         )
      );

      setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      setMinimumSize(getPreferredSize());
      setLocationRelativeTo(null);
      setAlwaysOnTop(true);
      setResizable(true);
      setVisible(true);
   }

   public static ToolBarCommand toolbar()
   {
      return new ToolBarCommand();
   }

   public static TabsCommand tabs()
   {
      return new TabsCommand();
   }

   public static TabCommand tab(String title)
   {
      return new TabCommand(title);
   }

   public static ButtonsCommand buttons()
   {
      return new ButtonsCommand();
   }

   public static SplitCommand split(Orientation orientation)
   {
      return new SplitCommand(orientation);
   }

   static class ToolBarCommand extends GridContainerCommand<JToolBar, ToolBarCommand>
   {
      @Override
      public JComponent getComponent(Orientation parentOrientation, ComponentFactory parentFactory)
      {
         withContentAnchorX(AX.LEFT);
         return super.getComponent(parentOrientation, parentFactory);
      }

      @Override
      protected JToolBar createDefaultContainer(ComponentFactory factory)
      {
         return new JToolBar();
      }

      @Override
      protected CellSpec getDefaultSpec(Orientation parentOrientation)
      {
         return spec().withWeightY(0.0);
      }
   }

   static class TabsCommand extends AbstractComponentCommand<TabsCommand>
   {
      private final List<TabCommand> tabs = new LinkedList<TabCommand>();

      @Override
      public JComponent getComponent(Orientation parentOrientation, ComponentFactory parentFactory)
      {
         JTabbedPane result = new JTabbedPane();
         int i = 0;
         for (TabCommand tab : tabs)
         {
            result.add(tab.getTitle(), panel().withContentAnchor(A.BOTH).add(tab).getComponent());
            if (tab.getIcon() != null)
            {
               result.setIconAt(i, tab.getIcon());
            }
            i++;
         }
         return result;
      }

      public TabsCommand add(TabCommand tab)
      {
         tabs.add(tab);
         return this;
      }
   }

   static class TabCommand extends GridContainerCommand<JPanel, TabCommand>
   {
      private String title;
      private Icon icon;

      public TabCommand(String title)
      {
         this.title = title;
      }

      String getTitle()
      {
         return title;
      }

      Icon getIcon()
      {
         return icon;
      }

      public TabCommand withIcon(Icon icon)
      {
         this.icon = icon;
         return this;
      }

      @Override
      protected CellSpec getDefaultSpec(Orientation parentOrientation)
      {
         return spec().withInset(5);
      }

      @Override
      protected JPanel createDefaultContainer(ComponentFactory factory)
      {
         return factory.createPanel();
      }
   }

   static class ButtonsCommand extends AbstractComponentCommand<ButtonsCommand>
   {
      List<JButton> buttons = new LinkedList<JButton>();

      public ButtonsCommand add(JButton button)
      {
         buttons.add(button);
         return this;
      }

      @Override
      public JComponent getComponent(Orientation parentOrientation, ComponentFactory parentFactory)
      {
         int maxWidth = 0;
         for (JButton button : buttons)
         {
            int width = button.getPreferredSize().width;
            if (width > maxWidth)
            {
               maxWidth = width;
            }
         }
         return panel().withContentAnchorX(AX.RIGHT).addAll(buttons, spec().withPreferredWidth(maxWidth)).getComponent();
      }

      @Override
      protected CellSpec getDefaultSpec(Orientation parentOrientation)
      {
         return spec().withWeightY(0.0);
      }
   }

   static class SplitCommand extends AbstractComponentCommand<SplitCommand>
   {
      private PanelCommand first;
      private PanelCommand second;

      private Orientation orientation = Orientation.HORIZONTAL;
      private double resizeWeight = 0.5;

      SplitCommand(Orientation orientation)
      {
         this.orientation = orientation;
      }

      @Override
      public JComponent getComponent(Orientation parentOrientation, ComponentFactory parentFactory)
      {
         JSplitPane result = new JSplitPane(orientation.isHorizontal() ? JSplitPane.HORIZONTAL_SPLIT : JSplitPane.VERTICAL_SPLIT);
         if (first != null)
         {
            result.setTopComponent(first.getComponent());
         }
         if (second != null)
         {
            result.setBottomComponent(second.getComponent());
         }
         result.setResizeWeight(resizeWeight);
         return result;
      }

      public SplitCommand withResizeWeight(double resizeWeight)
      {
         this.resizeWeight = resizeWeight;
         return this;
      }

      public SplitCommand withFirst(PanelCommand first)
      {
         this.first = first;
         return this;
      }

      public SplitCommand withSecond(PanelCommand second)
      {
         this.second = second;
         return this;
      }
   }

   public static void main(String[] args) throws Exception
   {
      setLookAndFeel(getSystemLookAndFeelClassName());
      new CustomCommandsDemo();
   }
}