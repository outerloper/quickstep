package org.quickstep.support;

import javax.swing.*;
import javax.swing.border.Border;

import org.quickstep.ComponentFactory;
import org.quickstep.GridBagToolKit;

import static javax.swing.BorderFactory.createCompoundBorder;

public class DecorationSupport<T>
{
   private final T owner;

   private Decoration<Border> borderDecoration;
   private Decoration<JScrollPane> scrollDecoration;

   public DecorationSupport(T owner)
   {
      this.owner = owner;
   }

   public final T withBorder()
   {
      return withBorderDecoration(new DefaultBorderDecoration(null));
   }

   public final T withBorder(String title)
   {
      return withBorderDecoration(new DefaultBorderDecoration(title));
   }

   private T withBorderDecoration(DefaultBorderDecoration borderDecoration)
   {
      this.borderDecoration = borderDecoration;
      return owner;
   }

   public final T withBorder(Border innerBorder, Border... outerBorders)
   {
      Border border = innerBorder;
      for (Border outerBorder : outerBorders)
      {
         border = createCompoundBorder(outerBorder, border);
      }
      borderDecoration = new CustomBorderDecoration(border);
      return owner;
   }

   public final T withScroll()
   {
      return withScrollDecoration(new DefaultScrollDecoration());
   }

   public final T withScroll(JScrollPane scroll)
   {
      return withScrollDecoration(new CustomScrollDecoration(scroll));
   }

   private T withScrollDecoration(Decoration<JScrollPane> scrollDecoration)
   {
      this.scrollDecoration = scrollDecoration;
      return owner;
   }

   public JComponent decorate(JComponent component, ComponentFactory componentFactory)
   {
      if (scrollDecoration != null)
      {
         JScrollPane scroll = scrollDecoration.getDecoration(componentFactory);
         scroll.setViewportView(component);
         component = new GridBagToolKit.ResizablePanel();
         component.add(scroll);
      }
      if (borderDecoration != null)
      {
         component.setBorder(borderDecoration.getDecoration(componentFactory));
      }

      return component;
   }

   static interface Decoration<D>
   {
      D getDecoration(ComponentFactory componentFactory);
   }

   static class DefaultBorderDecoration implements Decoration<Border>
   {
      private String title;

      DefaultBorderDecoration(String title)
      {
         this.title = title;
      }

      @Override
      public Border getDecoration(ComponentFactory componentFactory)
      {
         return componentFactory.createBorder(title);
      }
   }

   static class CustomBorderDecoration implements Decoration<Border>
   {
      private Border border;

      CustomBorderDecoration(Border border)
      {
         this.border = border;
      }

      @Override
      public Border getDecoration(ComponentFactory componentFactory)
      {
         return border;
      }
   }

   static class DefaultScrollDecoration implements Decoration<JScrollPane>
   {
      @Override
      public JScrollPane getDecoration(ComponentFactory componentFactory)
      {
         return componentFactory.createScrollPane();
      }
   }

   static class CustomScrollDecoration implements Decoration<JScrollPane>
   {
      private JScrollPane scrollPane;

      CustomScrollDecoration(JScrollPane scrollPane)
      {
         this.scrollPane = scrollPane;
      }

      @Override
      public JScrollPane getDecoration(ComponentFactory componentFactory)
      {
         return scrollPane;
      }
   }
}
