/*
 * Copyright 2016-17 inpwtepydjuf@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mmarquee.automation.uiautomation;

import com.sun.jna.Function;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.COM.IUnknown;
import com.sun.jna.platform.win32.Guid;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.PointerByReference;

/**
 * Created by Mark Humphreys on 13/07/2016.
 */
public interface IUIAutomationTextPattern extends IUnknown {
    /**
     * The interface IID for QueryInterface et al
     */
    Guid.IID IID = new Guid.IID("{32EBA289-3583-42C9-9C59-3B6D9A1E9B6A}");

    int AddRef();
    int Release();
    WinNT.HRESULT QueryInterface(Guid.REFIID byValue, PointerByReference pointerByReference);

    int getSelection(PointerByReference result);
    int getVisibleRanges(PointerByReference ranges);
    int getDocumentRange(PointerByReference range);

    class Converter {
        private static int METHODS = 9; // 0-2 IUnknown, 3-8 IUIAutomationTextPattern

        public static IUIAutomationTextPattern PointerToInterface(final PointerByReference ptr) {
            final Pointer interfacePointer = ptr.getValue();
            final Pointer vTablePointer = interfacePointer.getPointer(0);
            final Pointer[] vTable = new Pointer[METHODS];
            vTablePointer.read(0, vTable, 0, vTable.length);
            return new IUIAutomationTextPattern() {
                // IUnknown

                @Override
                public WinNT.HRESULT QueryInterface(Guid.REFIID byValue, PointerByReference pointerByReference) {
                    Function f = Function.getFunction(vTable[0], Function.ALT_CONVENTION);
                    return new WinNT.HRESULT(f.invokeInt(new Object[]{interfacePointer, byValue, pointerByReference}));
                }

                @Override
                public int AddRef() {
                    Function f = Function.getFunction(vTable[1], Function.ALT_CONVENTION);
                    return f.invokeInt(new Object[]{interfacePointer});
                }

                public int Release() {
                    Function f = Function.getFunction(vTable[2], Function.ALT_CONVENTION);
                    return f.invokeInt(new Object[]{interfacePointer});
                }

                public int getSelection(PointerByReference result) {
                    Function f = Function.getFunction(vTable[5], Function.ALT_CONVENTION);
                    return f.invokeInt(new Object[]{interfacePointer, result});
                }

                public int getVisibleRanges(PointerByReference ranges) {
                    Function f = Function.getFunction(vTable[6], Function.ALT_CONVENTION);
                    return f.invokeInt(new Object[]{interfacePointer, ranges});
                }

                public int getDocumentRange(PointerByReference range) {
                    Function f = Function.getFunction(vTable[7], Function.ALT_CONVENTION);
                    return f.invokeInt(new Object[]{interfacePointer, range});
                }
            };
        }
    }
}
