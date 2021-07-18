#ifndef MaterialLight_H
#define MaterialLight_H


class MaterialLight
{
private:
	float difR;
	float difG;
	float difB;
	float ambR;
	float ambG;
	float ambB;
	float emiR;
	float emiG;
	float emiB;
	float speR;
	float speG;
	float speB;
	float shi;

public:
	MaterialLight();
	float getDifR() { return difR; }
	float getDifG() { return difG; }
	float getDifB() { return difB; }
	float getAmbR() { return ambR; }
	float getAmbG() { return ambG; }
	float getAmbB() { return ambB; }
	float getEmiR() { return emiR; }
	float getEmiG() { return emiG; }
	float getEmiB() { return emiB; }
	float getSpeR() { return speR; }
	float getSpeG() { return speG; }
	float getSpeB() { return speB; }
	float getShi() { return shi; }
	void setDifR(float r) { difR = r; }
	void setDifG(float g) { difG = g; }
	void setDifB(float b) { difB = b; }
	void setAmbR(float r) { ambR = r; }
	void setAmbG(float g) { ambG = g; }
	void setAmbB(float b) { ambB = b; }
	void setEmiR(float r) { emiR = r; }
	void setEmiG(float g) { emiG = g; }
	void setEmiB(float b) { emiB = b; }
	void setSpeR(float r) { speR = r; }
	void setSpeG(float g) { speG = g; }
	void setSpeB(float b) { speB = b; }
	void setShi(float s) { shi = s; }
};

#endif

