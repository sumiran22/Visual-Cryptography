# Visual-Cryptography
Supervisor- Khushboo taneja Group no-21 Course- B.Tech(CSE) Group memeber- Sumiran kumari Roll no-130101173 Satyam singh Roll no-130101157

Visual Cryptography Scheme (VCS)

Introduction: A visual cryptography scheme (VCS) is a kind of secret sharing scheme which allows the encoding of a secret image into shares distributed to participants. The beauty of such a scheme is that a set of qualified participants is able to recover the secret image without any cryptographic knowledge and computation devices. An extended visual cryptography scheme (EVCS) is a kind of VCS which consists of meaningful shares (compared to the random shares of traditional VCS). Purpose of a VCS with random shares the traditional VCS or simply the VCS. In general, a traditional VCS takes a secret image as input, and outputs shares that satisfy two conditions: 1) any qualified subset of shares can recover the secret image; 2) any forbidden subset of shares cannot obtain any information of the secret image other than the size of the secret image.

Existing System (Problem Statement): Visual cryptography is the art and science of encrypting the image in such a way that no-one apart from the sender and intended recipient even realizes the original image, a form of security through obscurity. By contrast, cryptography obscures the original image, but it does not conceal the fact that it is not the actual image.

Proposed System: Proposed system Visual cryptography provides a friendly environment to deal with images. Generally cryptography tools supports only one kind of image formats. Our application supports .gif and .png (portable network graphics) formatted images and our application has been developed using swing and applet technologies, hence provides a friendly environment to users.

ADVANTAGES OF PROPOSED SYSTEM:
EVCS is flexible in the sense that there exist two trade-offs between the share pixel expansion and the visual quality of the shares and between the secret image pixel expansion and the visual quality of the shares. This flexibility allows the dealer to choose the proper parameters for different applications.

Modules  Interface design using applet frame work  Visual cryptography implementation  Encoding  Decoding  Creating transparencies  Un-hiding image from transparency  Testing and integration

MODULES DESCRIPTION: Interface design using Applet frame work: In this module, we design user interface design using applet frame work. The user interface should be very easy and understandable to every user. So that anyone can access using our system.

Visual cryptography Implementation: This module is the core for the project, where we implement the Visual Cryptography. We used LZW Data Compression algorithm. The LZW data compression algorithm is applied for the gray scale image here. As a pre-processing step, a dictionary is prepared for the gray scale image. In this dictionary, the string replaces characters with single quotes.

Encoding: A high level view of the encoding algorithm is shown here:  Initialize the dictionary to contain all strings of length one.  Find the longest string W in the dictionary that matches the current input.  Emit the dictionary index for W to output and remove W from the input.  Add W followed by the next symbol in the input to the dictionary.

Decoding: The decoding algorithm works by reading a value from the encoded input and outputting the corresponding string from the initialized dictionary. At the same time it obtains the next value from the input, and adds to the dictionary the concatenation of the string just output and the first character of the string obtained by decoding the next input value.

In this way the decoder builds up a dictionary which is identical to that used by the encoder, and uses it to decode subsequent input values. Creating Transparencies:

This scheme provides theoretically perfect secrecy. An attacker who obtains either the transparency image or the screen image obtains no information at all about the encoded image since a black-white square on either image is equally likely to encode a clear or dark square in the original image. Another valuable property of visual cryptography is that we can create the second layer after distributing the first layer to produce any image we want. Given a known transparency image, we can select a screen image by choosing the appropriate squares to produce the desired image. One of the most obvious limitations of using visual cryptography in the past was the problem of the decoded image containing an overall gray effect due to the leftover black sub pixel from encoding. This occurred because the decoded image is not an exact preproduction, but an expansion of the original, with extra black pixel. Black pixel in the original document remains black pixel in the decoded version, but White pixel becomes gray. This resulted in a lot of contrast to the entire image. The extra black sub pixel in the image causes the image to become distorted.

Testing and integration: This is the final module, which consists of integration of Visual cryptography implementation module into interface design using applet viewer. Then we need to test with various images and formation of transparencies. The transparencies should be able to save and load into the user interface.

Software Requirements:

Front end: Java/J2EE technologies (Servlet, JSP), HTML, CSS, JS, JDBC Back end: Oracle Database 10g Express Edition. Middleware/Server: Apache Tomcat v7.0. IDE: Eclipse IDE for Java EE Developers Browser: Best result on Mozilla Firefox Operating System: Window XP (Minimum).

Hardware Requirements:

CPU: Pentium 4. RAM: 256 MB. Space on HD: 5 MB. Display: CRT, LCD.
