/*
 * Copyright (c) 2015, 2021, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package jdk.internal.module;

import java.lang.module.ModuleDescriptor;
import java.util.Map;
import java.util.Set;

/**
 * A SystemModules object reconstitutes module descriptors and other modules
 * attributes in an efficient way to avoid parsing module-info.class files at
 * startup. Implementations of this class are generated by the "system modules"
 * jlink plugin.
 *
 * @see SystemModuleFinders
 * @see jdk.tools.jlink.internal.plugins.SystemModulesPlugin
 */

interface SystemModules {

    /**
     * Returns false if the module reconstituted by this SystemModules object
     * have no overlapping packages. Returns true if there are overlapping
     * packages or unknown.
     */
    boolean hasSplitPackages();

    /**
     * Return false if the modules reconstituted by this SystemModules object
     * do not include any incubator modules. Returns true if there are
     * incubating modules or unknown.
     */
    boolean hasIncubatorModules();

    /**
     * Returns the non-empty array of ModuleDescriptor objects.
     */
    ModuleDescriptor[] moduleDescriptors();

    /**
     * Returns the array of ModuleTarget objects. The array elements correspond
     * to the array of ModuleDescriptor objects.
     */
    ModuleTarget[] moduleTargets();

    /**
     * Returns the array of ModuleHashes objects. The array elements correspond
     * to the array of ModuleDescriptor objects.
     */
    ModuleHashes[] moduleHashes();

    /**
     * Returns the array of ModuleResolution objects. The array elements correspond
     * to the array of ModuleDescriptor objects.
     */
    ModuleResolution[] moduleResolutions();

    /**
     * Returns the map representing readability graph for the modules reconstituted
     * by this SystemModules object.
     */
    Map<String, Set<String>> moduleReads();

    /**
     * Returns the map of module concealed packages to open. The map key is the
     * module name, the value is the set of concealed packages to open.
     */
    Map<String, Set<String>> concealedPackagesToOpen();

    /**
     * Returns the map of module exported packages to open. The map key is the
     * module name, the value is the set of exported packages to open.
     */
    Map<String, Set<String>> exportedPackagesToOpen();
}
