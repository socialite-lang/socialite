package socialite.parser.antlr;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class ColBit extends ColOpt {
    int nbits;
    public ColBit() { }
    public ColBit(int _nbits) {
        nbits = _nbits;
    }
    public ColOpt clone() { return new ColBit(nbits); }

    public int getNumBits() { return nbits; }

    public int hashCode() { return nbits; }
    public boolean equals(Object o) {
        if (!(o instanceof ColRange)) return false;
        ColBit bit = (ColBit)o;
        return nbits==bit.nbits;
    }
    public void readExternal(ObjectInput in) throws IOException,
            ClassNotFoundException {
		nbits = in.readInt();
	}
	@Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(nbits);
    }
}
