package com.mtsmda.keygen.desktop.validation.sequence;

import com.mtsmda.validation.structure.sequence.FifthOrder;
import com.mtsmda.validation.structure.sequence.FourthOrder;
import com.mtsmda.validation.structure.sequence.SixthOrder;

import javax.validation.GroupSequence;

/**
 * Created by dminzat on 9/20/2016.
 */
@GroupSequence(value = {FourthOrder.class, FifthOrder.class, SixthOrder.class})
public interface RequestToKeygenOrderGroupSequence {
}