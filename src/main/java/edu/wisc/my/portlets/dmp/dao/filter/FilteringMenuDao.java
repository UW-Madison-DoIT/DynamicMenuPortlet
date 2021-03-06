/**
 * Copyright 2007 The JA-SIG Collaborative.  All rights reserved.
 * See license distributed with this file and
 * available online at http://www.uportal.org/license.html
 */
package edu.wisc.my.portlets.dmp.dao.filter;

import edu.wisc.my.portlets.dmp.beans.MenuItem;
import edu.wisc.my.portlets.dmp.dao.MenuDao;

import java.util.Set;

/**
 * @author Eric Dalquist
 * @since 1.0
 */
public class FilteringMenuDao implements MenuDao {
    private MenuDao delegateMenuDao;
    
    /**
     * @return the delegateMenuDao
     */
    public MenuDao getDelegateMenuDao() {
        return this.delegateMenuDao;
    }
    /**
     * @param delegateMenuDao the delegateMenuDao to set
     */
    public void setDelegateMenuDao(MenuDao delegateMenuDao) {
        this.delegateMenuDao = delegateMenuDao;
    }
    
    /* (non-Javadoc)
     * @see edu.wisc.my.portlets.dmp.dao.MenuDao#getMenu(java.lang.String, java.lang.String[])
     */
    public MenuItem getMenu(String menuName, String[] userGroups) {
        final MenuItem menuItem = this.delegateMenuDao.getMenu(menuName);

        if (null == menuItem) {
            return null;
        }

        // the userGroups must include at least one group to which the root menu item is granted
        // or else getMenu() should return null as specified in the API.

        if (menuItem.hasMatchingGroup(userGroups)) {
            // user is authorized to see the root menu,
            // so return the not-null filtering-wrapped menu item.
            return new FilteringMenuItem(menuItem, userGroups);
        }

        // user groups not among those authorized to see root menu item, so return null as per
        // interface definition of this getMenu() method.
        return null;
    }

    
    /* (non-Javadoc)
     * @see edu.wisc.my.portlets.dmp.dao.MenuDao#deleteMenu(java.lang.String)
     */
    public void deleteMenu(String menuName) {
        this.delegateMenuDao.deleteMenu(menuName);
    }
    /* (non-Javadoc)
     * @see edu.wisc.my.portlets.dmp.dao.MenuDao#getMenu(java.lang.String)
     */
    public MenuItem getMenu(String menuName) {
        return this.delegateMenuDao.getMenu(menuName);
    }
    
    /* (non-Javadoc)
     * @see edu.wisc.my.portlets.dmp.dao.MenuDao#getPublishedMenuNames()
     */
    public String[] getPublishedMenuNames() {
        return this.delegateMenuDao.getPublishedMenuNames();
    }
    
    /* (non-Javadoc)
     * @see edu.wisc.my.portlets.dmp.dao.MenuDao#storeMenu(java.lang.String, edu.wisc.my.portlets.dmp.beans.MenuItem)
     */
    public void storeMenu(String menuName, MenuItem rootItem) {
        this.delegateMenuDao.storeMenu(menuName, rootItem);
    }
}
