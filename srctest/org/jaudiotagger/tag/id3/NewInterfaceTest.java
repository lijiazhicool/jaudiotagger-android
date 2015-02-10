package org.jaudiotagger.tag.id3;

import android.graphics.Bitmap;

import org.jaudiotagger.AbstractTestCase;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagField;
import org.jaudiotagger.tag.TagOptionSingleton;
import org.jaudiotagger.tag.TagTextField;
import org.jaudiotagger.tag.datatype.DataTypes;
import org.jaudiotagger.tag.id3.framebody.AbstractFrameBodyTextInfo;
import org.jaudiotagger.tag.id3.framebody.FrameBodyAPIC;
import org.jaudiotagger.tag.id3.framebody.FrameBodyCOMM;
import org.jaudiotagger.tag.id3.framebody.FrameBodyPOPM;
import org.jaudiotagger.tag.id3.framebody.FrameBodyTALB;
import org.jaudiotagger.tag.id3.framebody.FrameBodyTPE1;
import org.jaudiotagger.tag.id3.framebody.FrameBodyTPE1Test;
import org.jaudiotagger.tag.id3.framebody.FrameBodyTXXX;
import org.jaudiotagger.tag.id3.framebody.FrameBodyUFID;
import org.jaudiotagger.tag.id3.framebody.FrameBodyUSLT;
import org.jaudiotagger.tag.id3.valuepair.TextEncoding;
import org.jaudiotagger.utils.BitmapUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Testing retrofitting of entagged interfaces
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class NewInterfaceTest {
    private static final String V1_ARTIST = "V1ARTIST";

    public static final String ALBUM_TEST_STRING = "mellow gold";
    public static final String ALBUM_TEST_STRING2 = "odelay";



    /**
     *
     */
    @Before
    public void setUp()
    {
        TagOptionSingleton.getInstance().setToDefault();
    }

    /**
     *
     */
    @After
    public void tearDown()
    {
    }

    @Test
    public void testBasicWrite()
    {
        Exception ex = null;
        try
        {
            File testFile = AbstractTestCase.copyAudioToTmp("testV1.mp3", new File("testBasicWrite.mp3"));
            org.jaudiotagger.audio.AudioFile audioFile = org.jaudiotagger.audio.AudioFileIO.read(testFile);
            org.jaudiotagger.tag.Tag newTag = audioFile.getTag();
            if (audioFile.getTag() == null)
            {
                audioFile.setTag(new ID3v23Tag());
                newTag = audioFile.getTag();
            }

            newTag.setField(FieldKey.ALBUM,"album");
            newTag.setField(FieldKey.ARTIST,"artist");
            newTag.setField(FieldKey.COMMENT,"comment");
            newTag.setField(FieldKey.GENRE,"Rock");
            newTag.setField(FieldKey.TITLE,"title");
            newTag.setField(FieldKey.YEAR,"year");
            newTag.setField(FieldKey.TRACK,Integer.toString(1));

            Assert.assertEquals("album", newTag.getFirst(FieldKey.ALBUM));
            Assert.assertEquals("artist", newTag.getFirst(FieldKey.ARTIST));
            Assert.assertEquals("comment", newTag.getFirst(FieldKey.COMMENT));
            Assert.assertEquals("Rock", newTag.getFirst(FieldKey.GENRE));
            Assert.assertEquals("title", newTag.getFirst(FieldKey.TITLE));
            Assert.assertEquals("year", newTag.getFirst(FieldKey.YEAR));
            Assert.assertEquals("1", newTag.getFirst(FieldKey.TRACK));

            audioFile.commit();

            audioFile = org.jaudiotagger.audio.AudioFileIO.read(testFile);
            newTag = audioFile.getTag();
            Assert.assertEquals("album", newTag.getFirst(FieldKey.ALBUM));
            Assert.assertEquals("artist", newTag.getFirst(FieldKey.ARTIST));
            Assert.assertEquals("comment", newTag.getFirst(FieldKey.COMMENT));
            Assert.assertEquals("Rock", newTag.getFirst(FieldKey.GENRE));
            Assert.assertEquals("title", newTag.getFirst(FieldKey.TITLE));
            Assert.assertEquals("year", newTag.getFirst(FieldKey.YEAR));
            Assert.assertEquals("1", newTag.getFirst(FieldKey.TRACK));
            AbstractTagFrameBody body = (((ID3v23Frame) newTag.getFirstField(ID3v23FieldKey.ALBUM.getFrameId())).getBody());
            Assert.assertEquals(TextEncoding.ISO_8859_1, body.getTextEncoding());

            TagOptionSingleton.getInstance().setId3v23DefaultTextEncoding(TextEncoding.UTF_16);
            TagOptionSingleton.getInstance().setResetTextEncodingForExistingFrames(true);
            audioFile.commit();
            audioFile = org.jaudiotagger.audio.AudioFileIO.read(testFile);
            newTag = audioFile.getTag();
            Assert.assertEquals("album", newTag.getFirst(FieldKey.ALBUM));
            Assert.assertEquals("artist", newTag.getFirst(FieldKey.ARTIST));
            Assert.assertEquals("comment", newTag.getFirst(FieldKey.COMMENT));
            Assert.assertEquals("Rock", newTag.getFirst(FieldKey.GENRE));
            Assert.assertEquals("title", newTag.getFirst(FieldKey.TITLE));
            Assert.assertEquals("year", newTag.getFirst(FieldKey.YEAR));
            Assert.assertEquals("1", newTag.getFirst(FieldKey.TRACK));
            body = (((ID3v23Frame) newTag.getFirstField(ID3v23FieldKey.ALBUM.getFrameId())).getBody());
            Assert.assertEquals(TextEncoding.UTF_16, body.getTextEncoding());

            TagOptionSingleton.getInstance().setId3v23DefaultTextEncoding(TextEncoding.ISO_8859_1);
            TagOptionSingleton.getInstance().setResetTextEncodingForExistingFrames(true);
            audioFile.commit();
            audioFile = org.jaudiotagger.audio.AudioFileIO.read(testFile);
            newTag = audioFile.getTag();
            body = (((ID3v23Frame) newTag.getFirstField(ID3v23FieldKey.ALBUM.getFrameId())).getBody());
            Assert.assertEquals(TextEncoding.ISO_8859_1, body.getTextEncoding());
            TagOptionSingleton.getInstance().setResetTextEncodingForExistingFrames(false);
        }
        catch (Exception e)
        {
            ex = e;
            ex.printStackTrace();
        }
        Assert.assertNull(ex);
    }

    @Test
    public void testNewInterfaceBasicReadandWriteID3v1() throws Exception
    {
        Exception e = null;
        File testFile = AbstractTestCase.copyAudioToTmp("testV1.mp3", new File("testnewIntId3v1.mp3"));
        MP3File mp3File = new MP3File(testFile);

        //Has no tag at this point
        Assert.assertFalse(mp3File.hasID3v1Tag());
        Assert.assertFalse(mp3File.hasID3v2Tag());

        //Create v1 tag (old method)
        ID3v11Tag v1tag = new ID3v11Tag();
        v1tag.setField(FieldKey.ARTIST,V1_ARTIST);
        v1tag.setField(FieldKey.ALBUM,"V1ALBUM" + "\u01ff");         //Note always convert to single byte so will be written as FF
        v1tag.setField(v1tag.createField(FieldKey.TITLE, "title"));
        v1tag.setField(FieldKey.GENRE,"Rock");
        v1tag.setField(v1tag.createField(FieldKey.TRACK, "12"));
        mp3File.setID3v1Tag(v1tag);
        mp3File.save();

        //Has only v1 tag
        Assert.assertTrue(mp3File.hasID3v1Tag());
        Assert.assertFalse(mp3File.hasID3v2Tag());

        //Read fields
        AudioFile af = AudioFileIO.read(testFile);
        Assert.assertEquals(V1_ARTIST, af.getTag().getFirst(FieldKey.ARTIST));
        Assert.assertEquals(V1_ARTIST, af.getTag().getFirst(FieldKey.ARTIST));
        Assert.assertEquals(V1_ARTIST, af.getTag().getFirst(FieldKey.ARTIST));
        Assert.assertEquals("V1ALBUM" + "\u00ff", af.getTag().getFirst(FieldKey.ALBUM));  //Lost the 00, is that what we want
        Assert.assertEquals("title", af.getTag().getFirst(FieldKey.TITLE));
        Assert.assertEquals("title", af.getTag().getFirst(FieldKey.TITLE));
        Assert.assertEquals("Rock", af.getTag().getFirst(FieldKey.GENRE));
        Assert.assertEquals("12", af.getTag().getFirst(FieldKey.TRACK));
        Assert.assertEquals("12", af.getTag().getFirst(FieldKey.TRACK));

        //Delete some fields (just sets string to empty String)
        af.getTag().deleteField(FieldKey.TITLE);
        Assert.assertEquals("", af.getTag().getFirst(FieldKey.TITLE));

        //Modify a value
        af.getTag().setField(FieldKey.TITLE,"newtitle");
        Assert.assertEquals("newtitle", af.getTag().getFirst(FieldKey.TITLE));

        //Adding just replaces current value
        af.getTag().addField(FieldKey.TITLE,"newtitle2");
        Assert.assertEquals("newtitle2", af.getTag().getFirst(FieldKey.TITLE));
    }

    @Test
    public void testNewInterfaceBasicReadandWriteID3v24() throws Exception
    {
        Exception e = null;
        File testFile = AbstractTestCase.copyAudioToTmp("testV1.mp3", new File("testnewIntId3v24.mp3"));
        MP3File mp3File = new MP3File(testFile);

        //Has no tag at this point
        Assert.assertFalse(mp3File.hasID3v1Tag());
        Assert.assertFalse(mp3File.hasID3v2Tag());

        //Create v1 tag (old method)
        ID3v11Tag v1tag = new ID3v11Tag();
        v1tag.setField(FieldKey.ARTIST,V1_ARTIST);
        v1tag.setField(FieldKey.ALBUM,"V1ALBUM");
        mp3File.setID3v1Tag(v1tag);
        mp3File.save();

        //Has only v1 tag at this point
        Assert.assertTrue(mp3File.hasID3v1Tag());
        Assert.assertFalse(mp3File.hasID3v2Tag());

        //Read back artist (new method ,v1)
        AudioFile af = AudioFileIO.read(testFile);
        Assert.assertEquals(V1_ARTIST, af.getTag().getFirst(FieldKey.ARTIST));
        Assert.assertEquals(V1_ARTIST, af.getTag().getFirst(FieldKey.ARTIST));
        Assert.assertEquals(V1_ARTIST, af.getTag().getFirst(FieldKey.ARTIST));

        //Add artist frame (old method)
        ID3v24Tag tag = new ID3v24Tag();
        ID3v24Frame frame = new ID3v24Frame(ID3v24Frames.FRAME_ID_ARTIST);
        ((FrameBodyTPE1) frame.getBody()).setText(FrameBodyTPE1Test.TPE1_TEST_STRING);
        tag.setFrame(frame);
        mp3File.setID3v2TagOnly(tag);
        mp3File.save();

        //Has v1 and v2 tag at this point
        Assert.assertTrue(mp3File.hasID3v1Tag());
        Assert.assertTrue(mp3File.hasID3v2Tag());

        //Read back artist (new method ,v1 value overridden by v2 method)
        af = AudioFileIO.read(testFile);
        Assert.assertEquals(FrameBodyTPE1Test.TPE1_TEST_STRING, af.getTag().getFirst(FieldKey.ARTIST));
        Assert.assertEquals(FrameBodyTPE1Test.TPE1_TEST_STRING, af.getTag().getFirst(FieldKey.ARTIST));
        Assert.assertEquals(FrameBodyTPE1Test.TPE1_TEST_STRING, ((ID3v24Tag) af.getTag()).getFirst(ID3v24FieldKey.ARTIST));
        Assert.assertEquals(FrameBodyTPE1Test.TPE1_TEST_STRING, ((TagTextField) af.getTag().getFirstField(FieldKey.ARTIST)).getContent());
        //.... but v1 value is still there
        Assert.assertEquals(V1_ARTIST, ((MP3File) af).getID3v1Tag().getFirst(FieldKey.ARTIST));
        //Write album ( new method)
        af.getTag().setField(FieldKey.ALBUM,ALBUM_TEST_STRING);
        af.commit();

        //Read back album (new method)
        af = AudioFileIO.read(testFile);
        Assert.assertEquals(ALBUM_TEST_STRING, af.getTag().getFirst(FieldKey.ALBUM));
        Assert.assertEquals(ALBUM_TEST_STRING, af.getTag().getFirst(FieldKey.ALBUM));
        Assert.assertEquals(ALBUM_TEST_STRING, ((ID3v24Tag) af.getTag()).getFirst(ID3v24FieldKey.ALBUM));
        Assert.assertEquals(ALBUM_TEST_STRING, ((TagTextField) af.getTag().getFirstField(FieldKey.ALBUM)).getContent());
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.ALBUM).size());

        //Read back album (old method)
        AbstractID3v2Frame checkframe = (AbstractID3v2Frame) ((MP3File) af).getID3v2Tag().getFrame(ID3v24Frames.FRAME_ID_ALBUM);
        Assert.assertEquals(ALBUM_TEST_STRING, ((FrameBodyTALB) checkframe.getBody()).getText());

        //If addField again, the value gets appended using the null char sperator system
        af.getTag().addField(FieldKey.ALBUM,ALBUM_TEST_STRING2);
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals(ALBUM_TEST_STRING, af.getTag().getFirst(FieldKey.ALBUM));
        Assert.assertEquals(ALBUM_TEST_STRING2, af.getTag().getValue(FieldKey.ALBUM, 1));
        Assert.assertEquals(ALBUM_TEST_STRING, ((ID3v24Tag) af.getTag()).getFirst(ID3v24FieldKey.ALBUM));
        Assert.assertEquals("mellow gold\0odelay", ((TagTextField) af.getTag().getFirstField(FieldKey.ALBUM)).getContent());
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.ALBUM).size());

        //And can replace existing value
        af.getTag().setField(FieldKey.ALBUM,ALBUM_TEST_STRING2);
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals(ALBUM_TEST_STRING2, af.getTag().getFirst(FieldKey.ALBUM));
        Assert.assertEquals(ALBUM_TEST_STRING2, af.getTag().getFirst(FieldKey.ALBUM));
        Assert.assertEquals(ALBUM_TEST_STRING2, ((ID3v24Tag) af.getTag()).getFirst(ID3v24FieldKey.ALBUM));
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.ALBUM).size());

        //and deleteField it
        af.getTag().deleteField(FieldKey.ALBUM);
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals("", af.getTag().getFirst(FieldKey.ALBUM));
        Assert.assertEquals("", af.getTag().getFirst(FieldKey.ALBUM));
        Assert.assertEquals("", ((ID3v24Tag) af.getTag()).getFirst(ID3v24FieldKey.ALBUM));
        Assert.assertEquals(0, af.getTag().getFields(FieldKey.ALBUM).size());

        //Test out the other basic fields
        //Year
        af.getTag().setField(FieldKey.YEAR,"1991");
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals("1991", af.getTag().getFirst(FieldKey.YEAR));
        Assert.assertEquals("1991", af.getTag().getFirst(FieldKey.YEAR));
        Assert.assertEquals("1991", ((ID3v24Tag) af.getTag()).getFirst(ID3v24FieldKey.YEAR));
        Assert.assertEquals("1991", ((TagTextField) af.getTag().getFirstField(FieldKey.YEAR)).getContent());
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.YEAR).size());
        Assert.assertEquals(2, af.getTag().getFieldCount());

        //Title
        af.getTag().setField(FieldKey.TITLE,"Title");
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals("Title", af.getTag().getFirst(FieldKey.TITLE));
        Assert.assertEquals("Title", af.getTag().getFirst(FieldKey.TITLE));
        Assert.assertEquals("Title", ((ID3v24Tag) af.getTag()).getFirst(ID3v24FieldKey.TITLE));
        Assert.assertEquals("Title", ((TagTextField) af.getTag().getFirstField(FieldKey.TITLE)).getContent());
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.TITLE).size());
        Assert.assertEquals(3, af.getTag().getFieldCount());

        //Comment, trickier because uses different framebody subclass to the ones above
        af.getTag().setField(FieldKey.COMMENT,"Comment");
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals("Comment", af.getTag().getFirst(FieldKey.COMMENT));
        Assert.assertEquals("Comment", ((ID3v24Tag) af.getTag()).getFirst(ID3v24FieldKey.COMMENT));

        //By default comments are created with empty description because this is what expected
        //by plyers such as iTunes.
        ID3v24Frame commentFrame = (ID3v24Frame) ((ID3v24Tag) af.getTag()).getFrame("COMM");
        FrameBodyCOMM fb = (FrameBodyCOMM) commentFrame.getBody();
        Assert.assertEquals("", fb.getDescription());
        Assert.assertEquals("Comment", fb.getText());
        //Change description, cant do this with common interface
        fb.setDescription("test");
        //Because has different description the following setField will addField another comment rather than overwriting the first one
        af.getTag().setField(FieldKey.COMMENT,"Comment2");
        Assert.assertEquals(2, af.getTag().getFields(FieldKey.COMMENT).size());
        //Add third Comment
        List<TagField> comments = af.getTag().getFields(FieldKey.COMMENT);
        ((FrameBodyCOMM) ((ID3v24Frame) comments.get(1)).getBody()).setDescription("test2");
        af.getTag().setField(FieldKey.COMMENT,"Comment3");
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals(3, af.getTag().getFields(FieldKey.COMMENT).size());

        //Add fourth Comment (but duplicate key - so overwrites 3rd comment)
        af.getTag().setField(FieldKey.COMMENT,"Comment4");
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals(3, af.getTag().getFields(FieldKey.COMMENT).size());

        //Remove all Comment tags
        af.getTag().deleteField(FieldKey.COMMENT);
        Assert.assertEquals(0, af.getTag().getFields(FieldKey.COMMENT).size());

        //Add first one back in
        af.getTag().setField(FieldKey.COMMENT,"Comment");
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.COMMENT).size());
        Assert.assertEquals(4, af.getTag().getFieldCount());

        //Genre
        //TODO only one genre frame allowed, but that can contain multiple GENRE values, currently
        //must parse as one genre e.g 34 67
        af.getTag().setField(FieldKey.GENRE,"CustomGenre");
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals("CustomGenre", af.getTag().getFirst(FieldKey.GENRE));
        Assert.assertEquals("CustomGenre", af.getTag().getFirst(FieldKey.GENRE));
        Assert.assertEquals("CustomGenre", ((ID3v24Tag) af.getTag()).getFirst(ID3v24FieldKey.GENRE));
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.GENRE).size());
        Assert.assertEquals(5, af.getTag().getFieldCount());

        //Track
        af.getTag().setField(FieldKey.TRACK,"7");
        af.getTag().setField(af.getTag().createField(FieldKey.TRACK_TOTAL, "11"));
        Assert.assertEquals("7", af.getTag().getFirst(FieldKey.TRACK));
        Assert.assertEquals("11", af.getTag().getFirst(FieldKey.TRACK_TOTAL));
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals("7", af.getTag().getFirst(FieldKey.TRACK));
        Assert.assertEquals("7", af.getTag().getFirst(FieldKey.TRACK));
        Assert.assertEquals("7", ((ID3v24Tag) af.getTag()).getFirst(ID3v24FieldKey.TRACK));
        Assert.assertEquals("11", af.getTag().getFirst(FieldKey.TRACK_TOTAL));
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.TRACK).size());
        Assert.assertEquals(6, af.getTag().getFieldCount());

        //AmazonId
        //This is one of many fields that uses the TXXX frame, the logic is more complicated
        af.getTag().setField(af.getTag().createField(FieldKey.AMAZON_ID, "asin123456" + "\u01ff"));
        af.commit();
        af = AudioFileIO.read(testFile);

         //Mood
        af.getTag().setField(af.getTag().createField(FieldKey.MOOD, "mood"));
        af.commit();
        af = AudioFileIO.read(testFile);

        Assert.assertEquals("asin123456" + "\u01ff", af.getTag().getFirst(FieldKey.AMAZON_ID));
        Assert.assertEquals("asin123456" + "\u01ff", ((ID3v24Tag) af.getTag()).getFirst(ID3v24FieldKey.AMAZON_ID));
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.AMAZON_ID).size());
        Assert.assertEquals(8, af.getTag().getFieldCount());

        //Now addField another different field that also uses a TXXX frame
        af.getTag().setField(af.getTag().createField(FieldKey.MUSICIP_ID, "musicip_id"));
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals(2, ((List) ((ID3v24Tag) af.getTag()).getFrame("TXXX")).size());
        Assert.assertEquals("musicip_id", af.getTag().getFirst(FieldKey.MUSICIP_ID));
        Assert.assertEquals("musicip_id", ((ID3v24Tag) af.getTag()).getFirst(ID3v24FieldKey.MUSICIP_ID));
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.MUSICIP_ID).size());
        Assert.assertEquals("asin123456" + "\u01ff", af.getTag().getFirst(FieldKey.AMAZON_ID));
        Assert.assertEquals(9, af.getTag().getFieldCount());

        //Now addField yet another different field that also uses a TXXX frame
        af.getTag().setField(af.getTag().createField(FieldKey.MUSICBRAINZ_RELEASEID, "releaseid"));
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals(3, ((List) ((ID3v24Tag) af.getTag()).getFrame("TXXX")).size());
        Assert.assertEquals("musicip_id", af.getTag().getFirst(FieldKey.MUSICIP_ID));
        Assert.assertEquals("releaseid", af.getTag().getFirst(FieldKey.MUSICBRAINZ_RELEASEID));
        Assert.assertEquals("releaseid", ((TagTextField) af.getTag().getFirstField(FieldKey.MUSICBRAINZ_RELEASEID)).getContent());
        Assert.assertEquals("asin123456" + "\u01ff", af.getTag().getFirst(FieldKey.AMAZON_ID));
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.MUSICIP_ID).size());
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.AMAZON_ID).size());
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.MUSICBRAINZ_RELEASEID).size());
        Assert.assertEquals(10, af.getTag().getFieldCount());

        //Now deleteField field
        af.getTag().deleteField(FieldKey.MUSICBRAINZ_RELEASEID);
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals(2, ((List) ((ID3v24Tag) af.getTag()).getFrame("TXXX")).size());
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.MUSICIP_ID).size());
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.AMAZON_ID).size());
        Assert.assertEquals(9, af.getTag().getFieldCount());

        //Cover Art:invalid way to do it
        try
        {
            af.getTag().setField(af.getTag().createField(FieldKey.COVER_ART, "coverart"));
        }
        catch (java.lang.UnsupportedOperationException uoe)
        {
            e = uoe;
        }
        Assert.assertTrue(e instanceof UnsupportedOperationException);

        //Add new image correctly
        RandomAccessFile imageFile = new RandomAccessFile(new File("testdata", "coverart.png"), "r");
        byte[] imagedata = new byte[(int) imageFile.length()];
        imageFile.read(imagedata);
        af.getTag().addField(tag.createArtworkField(imagedata, "image/png"));
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals(1, ((ID3v24Tag) af.getTag()).getFields(FieldKey.COVER_ART).size());
        Assert.assertEquals(1, ((ID3v24Tag) af.getTag()).getFields(ID3v24FieldKey.COVER_ART.getFieldName()).size());
        //TODO This isnt very user friendly
        TagField tagField = af.getTag().getFirstField(ID3v24FieldKey.COVER_ART.getFieldName());
        Assert.assertEquals("image/png::18545", ((TagTextField) af.getTag().getFirstField(FieldKey.COVER_ART)).getContent());

        Assert.assertTrue(tagField instanceof ID3v24Frame);
        ID3v24Frame apicFrame = (ID3v24Frame) tagField;
        Assert.assertTrue(apicFrame.getBody() instanceof FrameBodyAPIC);
        FrameBodyAPIC apicframebody = (FrameBodyAPIC) apicFrame.getBody();
        Assert.assertFalse(apicframebody.isImageUrl());
        Assert.assertEquals(10, af.getTag().getFieldCount());

        //Add another image correctly
        imageFile = new RandomAccessFile(new File("testdata", "coverart_small.png"), "r");
        imagedata = new byte[(int) imageFile.length()];
        imageFile.read(imagedata);
        af.getTag().addField(tag.createArtworkField(imagedata, "image/png"));
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals(2, af.getTag().getFields(FieldKey.COVER_ART).size());
        Assert.assertEquals(11, af.getTag().getFieldCount());

        //Actually createField the image from the read data
        Bitmap bi = null;
        TagField imageField = af.getTag().getFields(FieldKey.COVER_ART).get(0);
        if (imageField instanceof AbstractID3v2Frame)
        {
            FrameBodyAPIC imageFrameBody = (FrameBodyAPIC) ((AbstractID3v2Frame) imageField).getBody();
            if (!imageFrameBody.isImageUrl())
            {
                byte[] imageRawData = (byte[]) imageFrameBody.getObjectValue(DataTypes.OBJ_PICTURE_DATA);
                bi = BitmapUtils.decodeByteArray(imageRawData);
            }
        }
        Assert.assertNotNull(bi);

        //Add a linked Image
        af.getTag().addField(tag.createLinkedArtworkField("../testdata/coverart.jpg"));
        af.commit();

        af = AudioFileIO.read(testFile);
        Assert.assertEquals(3, af.getTag().getFields(FieldKey.COVER_ART).size());
        Assert.assertEquals(12, af.getTag().getFieldCount());
        List<TagField> imageFields = af.getTag().getFields(FieldKey.COVER_ART);
        tagField = imageFields.get(2);
        apicFrame = (ID3v24Frame) tagField;
        Assert.assertTrue(apicFrame.getBody() instanceof FrameBodyAPIC);
        apicframebody = (FrameBodyAPIC) apicFrame.getBody();
        Assert.assertTrue(apicframebody.isImageUrl());
        Assert.assertEquals("../testdata/coverart.jpg", apicframebody.getImageUrl());


    }

    /*  public void testReadUrlImage() throws Exception
       {
           Exception ex = null;
           try
           {
               File testFile = AbstractTestCase.copyAudioToTmp("testV1withurlimage.mp3");
               org.jaudiotagger.audio.AudioFile audioFile = org.jaudiotagger.audio.AudioFileIO.read(testFile);
               ID3v23Tag newTag = (ID3v23Tag)audioFile.getTag();
               assertEquals(1, newTag.getFields(FieldKey.COVER_ART).size());
               TagField tagField = newTag.getFirstField(ID3v23FieldKey.COVER_ART.getFieldName());
               assertTrue(tagField instanceof ID3v23Frame);
               ID3v23Frame apicFrame = (ID3v23Frame)tagField;
               assertTrue(apicFrame.getBody() instanceof FrameBodyAPIC);
               FrameBodyAPIC apicframebody = (FrameBodyAPIC)apicFrame.getBody();
               assertFalse(apicframebody.isImageUrl());
           }
           catch(Exception e)
           {
               ex=e;
               ex.printStackTrace();
           }
           assertNull(ex);
       }
    */
    @Test
    public void testNewInterfaceBasicReadandWriteID3v23() throws Exception
    {
        Exception e = null;
        File testFile = AbstractTestCase.copyAudioToTmp("testV1.mp3", new File("testnewIntId3v23.mp3"));
        MP3File mp3File = new MP3File(testFile);

        //Has no tag at this point
        Assert.assertFalse(mp3File.hasID3v1Tag());
        Assert.assertFalse(mp3File.hasID3v2Tag());

        //Create v1 tag (old method)
        ID3v11Tag v1tag = new ID3v11Tag();
        v1tag.setField(FieldKey.ARTIST,V1_ARTIST);
        v1tag.setField(FieldKey.ALBUM,"V1ALBUM");
        mp3File.setID3v1Tag(v1tag);
        mp3File.save();

        //Has only v1 tag at this point
        Assert.assertTrue(mp3File.hasID3v1Tag());
        Assert.assertFalse(mp3File.hasID3v2Tag());

        //Read back artist (new method ,v1)
        AudioFile af = AudioFileIO.read(testFile);
        Assert.assertEquals(V1_ARTIST, af.getTag().getFirst(FieldKey.ARTIST));
        Assert.assertEquals(V1_ARTIST, af.getTag().getFirst(FieldKey.ARTIST));
        Assert.assertEquals(V1_ARTIST, af.getTag().getFirst(FieldKey.ARTIST));

        //Add artist frame (old method)
        ID3v23Tag tag = new ID3v23Tag();
        ID3v23Frame frame = new ID3v23Frame(ID3v23Frames.FRAME_ID_V3_ARTIST);
        ((FrameBodyTPE1) frame.getBody()).setText(FrameBodyTPE1Test.TPE1_TEST_STRING);
        tag.setFrame(frame);
        mp3File.setID3v2TagOnly(tag);
        mp3File.save();

        //Has v1 and v2 tag at this point
        Assert.assertTrue(mp3File.hasID3v1Tag());
        Assert.assertTrue(mp3File.hasID3v2Tag());

        //Read back artist (new method ,v1 value overrriden by v2 method)
        af = AudioFileIO.read(testFile);
        Assert.assertEquals(FrameBodyTPE1Test.TPE1_TEST_STRING, af.getTag().getFirst(FieldKey.ARTIST));
        Assert.assertEquals(FrameBodyTPE1Test.TPE1_TEST_STRING, af.getTag().getFirst(FieldKey.ARTIST));
        Assert.assertEquals(FrameBodyTPE1Test.TPE1_TEST_STRING, ((ID3v23Tag) af.getTag()).getFirst(ID3v23FieldKey.ARTIST));
        //.... but v1 value is still there
        Assert.assertEquals(V1_ARTIST, ((MP3File) af).getID3v1Tag().getFirst(FieldKey.ARTIST));
        //Write album ( new method)
        af.getTag().setField(FieldKey.ALBUM,ALBUM_TEST_STRING);
        af.commit();

        //Read back album (new method)
        af = AudioFileIO.read(testFile);
        Assert.assertEquals(ALBUM_TEST_STRING, af.getTag().getFirst(FieldKey.ALBUM));
        Assert.assertEquals(ALBUM_TEST_STRING, af.getTag().getFirst(FieldKey.ALBUM));
        Assert.assertEquals(ALBUM_TEST_STRING, ((ID3v23Tag) af.getTag()).getFirst(ID3v23FieldKey.ALBUM));
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.ALBUM).size());

        //Read back album (old method)
        AbstractID3v2Frame checkframe = (AbstractID3v2Frame) ((MP3File) af).getID3v2Tag().getFrame(ID3v23Frames.FRAME_ID_V3_ALBUM);
        Assert.assertEquals(ALBUM_TEST_STRING, ((FrameBodyTALB) checkframe.getBody()).getText());

        //If add smae field again appended to existiong frame
        af.getTag().addField(FieldKey.ALBUM,ALBUM_TEST_STRING2);
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals(ALBUM_TEST_STRING, af.getTag().getFirst(FieldKey.ALBUM));
        Assert.assertEquals(ALBUM_TEST_STRING, ((ID3v23Tag) af.getTag()).getFirst(ID3v23FieldKey.ALBUM));
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.ALBUM).size());

        //But can replace existing value
        af.getTag().setField(FieldKey.ALBUM,ALBUM_TEST_STRING2);
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals(ALBUM_TEST_STRING2, af.getTag().getFirst(FieldKey.ALBUM));
        Assert.assertEquals(ALBUM_TEST_STRING2, af.getTag().getFirst(FieldKey.ALBUM));
        Assert.assertEquals(ALBUM_TEST_STRING2, ((ID3v23Tag) af.getTag()).getFirst(ID3v23FieldKey.ALBUM));
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.ALBUM).size());

        //and deleteField it
        af.getTag().deleteField(FieldKey.ALBUM);
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals("", af.getTag().getFirst(FieldKey.ALBUM));
        Assert.assertEquals("", af.getTag().getFirst(FieldKey.ALBUM));
        Assert.assertEquals("", ((ID3v23Tag) af.getTag()).getFirst(ID3v23FieldKey.ALBUM));
        Assert.assertEquals(0, af.getTag().getFields(FieldKey.ALBUM).size());

        //Test out the other basic fields
        //Year
        af.getTag().setField(FieldKey.YEAR,"1991");
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals("1991", af.getTag().getFirst(FieldKey.YEAR));
        Assert.assertEquals("1991", af.getTag().getFirst(FieldKey.YEAR));
        Assert.assertEquals("1991", ((ID3v23Tag) af.getTag()).getFirst(ID3v23FieldKey.YEAR));
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.YEAR).size());
        Assert.assertEquals(2, af.getTag().getFieldCount());

        //Title
        af.getTag().setField(FieldKey.TITLE,"Title");
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals("Title", af.getTag().getFirst(FieldKey.TITLE));
        Assert.assertEquals("Title", af.getTag().getFirst(FieldKey.TITLE));
        Assert.assertEquals("Title", ((ID3v23Tag) af.getTag()).getFirst(ID3v23FieldKey.TITLE));
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.TITLE).size());
        Assert.assertEquals(3, af.getTag().getFieldCount());

        //Comment, trickier because uses different framebody subclass to the ones above
        af.getTag().setField(FieldKey.COMMENT,"Comment");
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals("Comment", af.getTag().getFirst(FieldKey.COMMENT));
        Assert.assertEquals("Comment", ((ID3v23Tag) af.getTag()).getFirst(ID3v23FieldKey.COMMENT));
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.COMMENT).size());
        Assert.assertEquals(4, af.getTag().getFieldCount());

        //Genre
        //TODO only one genre frame allowed, but that can contain multiple GENRE values, currently
        //must parse as one genre e.g 34 67
        af.getTag().setField(FieldKey.GENRE,"CustomGenre");
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals("CustomGenre", af.getTag().getFirst(FieldKey.GENRE));
        Assert.assertEquals("CustomGenre", ((ID3v23Tag) af.getTag()).getFirst(ID3v23FieldKey.GENRE));
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.GENRE).size());
        Assert.assertEquals(5, af.getTag().getFieldCount());


        //Track
        af.getTag().setField(FieldKey.TRACK,"7");
        af.getTag().setField(af.getTag().createField(FieldKey.TRACK_TOTAL, "11"));
        Assert.assertEquals("7", af.getTag().getFirst(FieldKey.TRACK));
        Assert.assertEquals("11", af.getTag().getFirst(FieldKey.TRACK_TOTAL));

        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals("7", af.getTag().getFirst(FieldKey.TRACK));
        Assert.assertEquals("7", af.getTag().getFirst(FieldKey.TRACK));
        Assert.assertEquals("7", ((ID3v23Tag) af.getTag()).getFirst(ID3v23FieldKey.TRACK));
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.TRACK).size());
        Assert.assertEquals(6, af.getTag().getFieldCount());

        Assert.assertEquals("7", af.getTag().getFirst(FieldKey.TRACK));
        
        //AmazonId also testing utf encoding here
        //This is one of many fields that uses the TXXX frame, the logic is more complicated
        af.getTag().setField(af.getTag().createField(FieldKey.AMAZON_ID, "asin123456" + "\u01ff"));
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals("asin123456" + "\u01ff", af.getTag().getFirst(FieldKey.AMAZON_ID));
        Assert.assertEquals("asin123456" + "\u01ff", ((ID3v23Tag) af.getTag()).getFirst(ID3v23FieldKey.AMAZON_ID));
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.AMAZON_ID).size());
        Assert.assertEquals(7, af.getTag().getFieldCount());

         //Mood
        af.getTag().setField(af.getTag().createField(FieldKey.MOOD, "mood"));
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals("mood", af.getTag().getFirst(FieldKey.MOOD));
        //Now deleteField field
        af.getTag().deleteField(FieldKey.MOOD);
        af.commit();
        af = AudioFileIO.read(testFile);

        Assert.assertEquals("7", af.getTag().getFirst(FieldKey.TRACK));

        //Track Total
        af.getTag().setField(af.getTag().createField(FieldKey.TRACK_TOTAL, "11"));
        Assert.assertEquals("11", af.getTag().getFirst(FieldKey.TRACK_TOTAL));
        
        //Now addField another different field that also uses a TXXX frame
        af.getTag().setField(af.getTag().createField(FieldKey.MUSICIP_ID, "musicip_id"));
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals(2, ((List) ((ID3v23Tag) af.getTag()).getFrame("TXXX")).size());
        Assert.assertEquals("musicip_id", af.getTag().getFirst(FieldKey.MUSICIP_ID));
        Assert.assertEquals("musicip_id", ((ID3v23Tag) af.getTag()).getFirst(ID3v23FieldKey.MUSICIP_ID));
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.MUSICIP_ID).size());
        Assert.assertEquals("asin123456" + "\u01ff", af.getTag().getFirst(FieldKey.AMAZON_ID));
        Assert.assertEquals("7", af.getTag().getFirst(FieldKey.TRACK));
        Assert.assertEquals(8, af.getTag().getFieldCount());
        Assert.assertEquals("11", af.getTag().getFirst(FieldKey.TRACK_TOTAL));
        Assert.assertEquals("7", ((ID3v23Tag) af.getTag()).getFirst(ID3v23FieldKey.TRACK));
        Assert.assertEquals("11", ((ID3v23Tag) af.getTag()).getFirst(ID3v23FieldKey.TRACK_TOTAL));
        
        //Now addField yet another different field that also uses a TXXX frame
        af.getTag().setField(af.getTag().createField(FieldKey.MUSICBRAINZ_RELEASEID, "releaseid"));
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals(3, ((List) ((ID3v23Tag) af.getTag()).getFrame("TXXX")).size());
        Assert.assertEquals("musicip_id", af.getTag().getFirst(FieldKey.MUSICIP_ID));
        Assert.assertEquals("releaseid", af.getTag().getFirst(FieldKey.MUSICBRAINZ_RELEASEID));
        Assert.assertEquals("asin123456" + "\u01ff", af.getTag().getFirst(FieldKey.AMAZON_ID));
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.MUSICIP_ID).size());
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.AMAZON_ID).size());
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.MUSICBRAINZ_RELEASEID).size());
        Assert.assertEquals(9, af.getTag().getFieldCount());

        //Now deleteField field
        af.getTag().deleteField(FieldKey.MUSICBRAINZ_RELEASEID);
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals(2, ((List) ((ID3v23Tag) af.getTag()).getFrame("TXXX")).size());
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.MUSICIP_ID).size());
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.AMAZON_ID).size());
        Assert.assertEquals(8, af.getTag().getFieldCount());

        //Cover Art:invalid way to do it
        try
        {
            af.getTag().setField(af.getTag().createField(FieldKey.COVER_ART, "coverart"));
        }
        catch (java.lang.UnsupportedOperationException uoe)
        {
            e = uoe;
        }
        Assert.assertTrue(e instanceof UnsupportedOperationException);

        //Add new image correctly
        RandomAccessFile imageFile = new RandomAccessFile(new File("testdata", "coverart.png"), "r");
        byte[] imagedata = new byte[(int) imageFile.length()];
        imageFile.read(imagedata);
        af.getTag().addField(tag.createArtworkField(imagedata, "image/png"));
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.COVER_ART).size());
        Assert.assertEquals(9, af.getTag().getFieldCount());

        //Add another image correctly
        imageFile = new RandomAccessFile(new File("testdata", "coverart_small.png"), "r");
        imagedata = new byte[(int) imageFile.length()];
        imageFile.read(imagedata);
        af.getTag().addField(tag.createArtworkField(imagedata, "image/png"));
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals(2, af.getTag().getFields(FieldKey.COVER_ART).size());
        Assert.assertEquals(10, af.getTag().getFieldCount());


    }

    @Test
    public void testNewInterfaceBasicReadandWriteID3v22() throws Exception
    {
        Exception e = null;
        File testFile = AbstractTestCase.copyAudioToTmp("testV1.mp3", new File("testnewIntId3v22.mp3"));
        MP3File mp3File = new MP3File(testFile);

        //Has no tag at this point
        Assert.assertFalse(mp3File.hasID3v1Tag());
        Assert.assertFalse(mp3File.hasID3v2Tag());

        //Create v1 tag (old method)
        ID3v11Tag v1tag = new ID3v11Tag();
        v1tag.setField(FieldKey.ARTIST,V1_ARTIST);
        v1tag.setField(FieldKey.ALBUM,"V1ALBUM");
        mp3File.setID3v1Tag(v1tag);
        mp3File.save();

        //Has only v1 tag at this point
        Assert.assertTrue(mp3File.hasID3v1Tag());
        Assert.assertFalse(mp3File.hasID3v2Tag());

        //Read back artist (new method ,v1)
        AudioFile af = AudioFileIO.read(testFile);
        Assert.assertEquals(V1_ARTIST, af.getTag().getFirst(FieldKey.ARTIST));
        Assert.assertEquals(V1_ARTIST, af.getTag().getFirst(FieldKey.ARTIST));
        Assert.assertEquals(V1_ARTIST, af.getTag().getFirst(FieldKey.ARTIST));

        //Add artist frame (old method)
        ID3v22Tag tag = new ID3v22Tag();
        ID3v22Frame frame = new ID3v22Frame(ID3v22Frames.FRAME_ID_V2_ARTIST);
        ((FrameBodyTPE1) frame.getBody()).setText(FrameBodyTPE1Test.TPE1_TEST_STRING);
        tag.setFrame(frame);
        mp3File.setID3v2TagOnly(tag);
        mp3File.save();

        //Has v1 and v2 tag at this point
        Assert.assertTrue(mp3File.hasID3v1Tag());
        Assert.assertTrue(mp3File.hasID3v2Tag());

        //Read back artist (new method ,v1 value overrriden by v2 method)
        af = AudioFileIO.read(testFile);
        Assert.assertEquals(FrameBodyTPE1Test.TPE1_TEST_STRING, af.getTag().getFirst(FieldKey.ARTIST));
        Assert.assertEquals(FrameBodyTPE1Test.TPE1_TEST_STRING, af.getTag().getFirst(FieldKey.ARTIST));

        Assert.assertEquals(FrameBodyTPE1Test.TPE1_TEST_STRING, ((ID3v22Tag) af.getTag()).getFirst(ID3v22FieldKey.ARTIST));
        //.... but v1 value is still there
        Assert.assertEquals(V1_ARTIST, ((MP3File) af).getID3v1Tag().getFirst(FieldKey.ARTIST));
        //Write album ( new method)
        af.getTag().setField(FieldKey.ALBUM,ALBUM_TEST_STRING);
        Assert.assertEquals(ALBUM_TEST_STRING, af.getTag().getFirst(FieldKey.ALBUM));
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.ALBUM).size());

        af.commit();

        //Read back album (new method)
        af = AudioFileIO.read(testFile);
        Assert.assertEquals(ALBUM_TEST_STRING, af.getTag().getFirst(FieldKey.ALBUM));
        Assert.assertEquals(ALBUM_TEST_STRING, af.getTag().getFirst(FieldKey.ALBUM));
        Assert.assertEquals(ALBUM_TEST_STRING, ((ID3v22Tag) af.getTag()).getFirst(ID3v22FieldKey.ALBUM));
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.ALBUM).size());

        //Read back album (old method)
        AbstractID3v2Frame checkframe = (AbstractID3v2Frame) ((MP3File) af).getID3v2Tag().getFrame(ID3v22Frames.FRAME_ID_V2_ALBUM);
        Assert.assertEquals(ALBUM_TEST_STRING, ((FrameBodyTALB) checkframe.getBody()).getText());

        //If add extra text field its appended to existing frame
        af.getTag().addField(FieldKey.ALBUM,ALBUM_TEST_STRING2);
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals(ALBUM_TEST_STRING, af.getTag().getFirst(FieldKey.ALBUM));
        Assert.assertEquals(ALBUM_TEST_STRING, af.getTag().getValue(FieldKey.ALBUM, 0));
        Assert.assertEquals(ALBUM_TEST_STRING2, af.getTag().getValue(FieldKey.ALBUM, 1));
        Assert.assertEquals(ALBUM_TEST_STRING, ((ID3v22Tag) af.getTag()).getFirst(ID3v22FieldKey.ALBUM));
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.ALBUM).size());

        //But can replace existing value
        af.getTag().setField(FieldKey.ALBUM,ALBUM_TEST_STRING2);
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals(ALBUM_TEST_STRING2, af.getTag().getFirst(FieldKey.ALBUM));
        Assert.assertEquals(ALBUM_TEST_STRING2, af.getTag().getFirst(FieldKey.ALBUM));
        Assert.assertEquals(ALBUM_TEST_STRING2, ((ID3v22Tag) af.getTag()).getFirst(ID3v22FieldKey.ALBUM));
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.ALBUM).size());

        //and deleteField it
        af.getTag().deleteField(FieldKey.ALBUM);
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals("", af.getTag().getFirst(FieldKey.ALBUM));
        Assert.assertEquals("", af.getTag().getFirst(FieldKey.ALBUM));
        Assert.assertEquals("", ((ID3v22Tag) af.getTag()).getFirst(ID3v22FieldKey.ALBUM));
        Assert.assertEquals(0, af.getTag().getFields(FieldKey.ALBUM).size());

        //Test out the other basic fields
        //Year
        af.getTag().setField(FieldKey.YEAR,"1991");
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals("1991", af.getTag().getFirst(FieldKey.YEAR));
        Assert.assertEquals("1991", af.getTag().getFirst(FieldKey.YEAR));
        Assert.assertEquals("1991", ((ID3v22Tag) af.getTag()).getFirst(ID3v22FieldKey.YEAR));
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.YEAR).size());
        Assert.assertEquals(2, af.getTag().getFieldCount());

        //Title
        af.getTag().setField(FieldKey.TITLE,"Title");
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals("Title", af.getTag().getFirst(FieldKey.TITLE));
        Assert.assertEquals("Title", af.getTag().getFirst(FieldKey.TITLE));
        Assert.assertEquals("Title", ((ID3v22Tag) af.getTag()).getFirst(ID3v22FieldKey.TITLE));
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.TITLE).size());
        Assert.assertEquals(3, af.getTag().getFieldCount());

        //Comment, trickier because uses different framebody subclass to the ones above
        af.getTag().setField(FieldKey.COMMENT,"Comment");
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals("Comment", af.getTag().getFirst(FieldKey.COMMENT));
        Assert.assertEquals("Comment", af.getTag().getFirst(FieldKey.COMMENT));
        Assert.assertEquals("Comment", ((ID3v22Tag) af.getTag()).getFirst(ID3v22FieldKey.COMMENT));
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.COMMENT).size());
        Assert.assertEquals(4, af.getTag().getFieldCount());

        //Genre
        //TODO only one genre frame allowed, but that can contain multiple GENRE values, currently
        //must parse as one genre e.g 34 67
        af.getTag().setField(FieldKey.GENRE,"CustomGenre");
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals("CustomGenre", af.getTag().getFirst(FieldKey.GENRE));
        Assert.assertEquals("CustomGenre", af.getTag().getFirst(FieldKey.GENRE));
        Assert.assertEquals("CustomGenre", ((ID3v22Tag) af.getTag()).getFirst(ID3v22FieldKey.GENRE));
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.GENRE).size());
        Assert.assertEquals(5, af.getTag().getFieldCount());

        //Track
        af.getTag().setField(FieldKey.TRACK,"7");
        af.getTag().setField(af.getTag().createField(FieldKey.TRACK_TOTAL, "11"));
        Assert.assertEquals("7", af.getTag().getFirst(FieldKey.TRACK));
        Assert.assertEquals("11", af.getTag().getFirst(FieldKey.TRACK_TOTAL));
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals("7", af.getTag().getFirst(FieldKey.TRACK));
        Assert.assertEquals("7", af.getTag().getFirst(FieldKey.TRACK));
        Assert.assertEquals("7", ((ID3v22Tag) af.getTag()).getFirst(ID3v22FieldKey.TRACK));
        Assert.assertEquals("11", af.getTag().getFirst(FieldKey.TRACK_TOTAL));
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.TRACK).size());
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.TRACK).size());
        Assert.assertEquals(6, af.getTag().getFieldCount());

        //AmazonId
        //This is one of many fields that uses the TXXX frame, the logic is more complicated
        af.getTag().setField(af.getTag().createField(FieldKey.AMAZON_ID, "asin123456" + "\u01ff"));
        af.commit();
        af = AudioFileIO.read(testFile);

        //Mood
        af.getTag().setField(af.getTag().createField(FieldKey.MOOD, "mood"));
        af.commit();
        af = AudioFileIO.read(testFile);
         Assert.assertEquals("mood", af.getTag().getFirst(FieldKey.MOOD));
        //Now deleteField field
        af.getTag().deleteField(FieldKey.MOOD);
        af.commit();
        af = AudioFileIO.read(testFile);

        Assert.assertEquals("asin123456" + "\u01ff", af.getTag().getFirst(FieldKey.AMAZON_ID));
        Assert.assertEquals("asin123456" + "\u01ff", ((ID3v22Tag) af.getTag()).getFirst(ID3v22FieldKey.AMAZON_ID));
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.AMAZON_ID).size());
        Assert.assertEquals(7, af.getTag().getFieldCount());

        //Now addField another different field that also uses a TXX frame
        af.getTag().setField(af.getTag().createField(FieldKey.MUSICIP_ID, "musicip_id"));
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals(2, ((List) ((ID3v22Tag) af.getTag()).getFrame("TXX")).size());
        Assert.assertEquals("musicip_id", af.getTag().getFirst(FieldKey.MUSICIP_ID));
        Assert.assertEquals("musicip_id", ((ID3v22Tag) af.getTag()).getFirst(ID3v22FieldKey.MUSICIP_ID));
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.MUSICIP_ID).size());
        Assert.assertEquals("asin123456" + "\u01ff", af.getTag().getFirst(FieldKey.AMAZON_ID));
        Assert.assertEquals(8, af.getTag().getFieldCount());

        //Now addField yet another different field that also uses a TXX frame
        af.getTag().setField(af.getTag().createField(FieldKey.MUSICBRAINZ_RELEASEID, "releaseid"));
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals(3, ((List) ((ID3v22Tag) af.getTag()).getFrame("TXX")).size());
        Assert.assertEquals("musicip_id", af.getTag().getFirst(FieldKey.MUSICIP_ID));
        Assert.assertEquals("releaseid", af.getTag().getFirst(FieldKey.MUSICBRAINZ_RELEASEID));
        Assert.assertEquals("asin123456" + "\u01ff", af.getTag().getFirst(FieldKey.AMAZON_ID));
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.MUSICIP_ID).size());
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.AMAZON_ID).size());
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.MUSICBRAINZ_RELEASEID).size());
        Assert.assertEquals(9, af.getTag().getFieldCount());

        //Now deleteField field
        af.getTag().deleteField(FieldKey.MUSICBRAINZ_RELEASEID);
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals(2, ((List) ((ID3v22Tag) af.getTag()).getFrame("TXX")).size());
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.MUSICIP_ID).size());
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.AMAZON_ID).size());
        Assert.assertEquals(8, af.getTag().getFieldCount());

        //Cover Art:invalid way to do it
        try
        {
            af.getTag().setField(af.getTag().createField(FieldKey.COVER_ART, "coverart"));
        }
        catch (java.lang.UnsupportedOperationException uoe)
        {
            e = uoe;
        }
        Assert.assertTrue(e instanceof UnsupportedOperationException);

        //Add new image correctly
        RandomAccessFile imageFile = new RandomAccessFile(new File("testdata", "coverart.png"), "r");
        byte[] imagedata = new byte[(int) imageFile.length()];
        imageFile.read(imagedata);
        af.getTag().addField(tag.createArtworkField(imagedata, "image/png"));
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals(1, af.getTag().getFields(FieldKey.COVER_ART).size());
        Assert.assertEquals(9, af.getTag().getFieldCount());

        //Add another image correctly
        imageFile = new RandomAccessFile(new File("testdata", "coverart_small.png"), "r");
        imagedata = new byte[(int) imageFile.length()];
        imageFile.read(imagedata);
        af.getTag().addField(tag.createArtworkField(imagedata, "image/png"));
        af.commit();
        af = AudioFileIO.read(testFile);
        Assert.assertEquals(2, af.getTag().getFields(FieldKey.COVER_ART).size());
        Assert.assertEquals(10, af.getTag().getFieldCount());
    }

    /**
     * Test how adding multiple frameswith new interface  of same type is is handled
     *
     * @throws Exception
     */
    @Test
    public void testSettingMultipleFramesofSameType() throws Exception
    {
        Exception e = null;
        File testFile = AbstractTestCase.copyAudioToTmp("testV1.mp3", new File("testSetMultiple.mp3"));
        AudioFile af = AudioFileIO.read(testFile);
        MP3File mp3File = (MP3File) af;
        ID3v24Tag tag = new ID3v24Tag();
        ID3v24Frame frame = new ID3v24Frame(ID3v24Frames.FRAME_ID_COMMENT);
        ((FrameBodyCOMM) frame.getBody()).setText("Comment");
        tag.setFrame(frame);
        mp3File.setID3v2TagOnly(tag);
        mp3File.save();
        af = AudioFileIO.read(testFile);
        mp3File = (MP3File) af;

        //COMM
        {
            ID3v24Frame commentFrame = (ID3v24Frame) ((ID3v24Tag) af.getTag()).getFrame("COMM");
            FrameBodyCOMM fb = (FrameBodyCOMM) commentFrame.getBody();
            Assert.assertEquals("", fb.getDescription());
            Assert.assertEquals("Comment", fb.getText());
            //Change description, cant do this with common interface
            fb.setDescription("test");
            //Because has different description the following setField will addField another comment rather than overwriting the first one
            af.getTag().setField(FieldKey.COMMENT,"Comment2");
            Assert.assertEquals(2, af.getTag().getFields(FieldKey.COMMENT).size());
            //Add third Comment
            List<TagField> comments = af.getTag().getFields(FieldKey.COMMENT);
            ((FrameBodyCOMM) ((ID3v24Frame) comments.get(1)).getBody()).setDescription("test2");
            af.getTag().setField(FieldKey.COMMENT,"Comment3");
            af.commit();
            af = AudioFileIO.read(testFile);
            Assert.assertEquals(3, af.getTag().getFields(FieldKey.COMMENT).size());

            //Add fourth Comment (but duplicate key - so overwrites 3rd comment)
            af.getTag().setField(FieldKey.COMMENT,"Comment4");
            af.commit();
            af = AudioFileIO.read(testFile);
            Assert.assertEquals(3, af.getTag().getFields(FieldKey.COMMENT).size());

            //Add comment using generic call
            af.getTag().setField(af.getTag().createField(FieldKey.COMMENT, "abcdef-ghijklmn"));

            //Remove all Comment tags
            af.getTag().deleteField(FieldKey.COMMENT);
            Assert.assertEquals(0, af.getTag().getFields(FieldKey.COMMENT).size());

            //Add first one back in
            af.getTag().setField(FieldKey.COMMENT,"Comment");
            af.commit();
            af = AudioFileIO.read(testFile);
            Assert.assertEquals(1, af.getTag().getFields(FieldKey.COMMENT).size());
            Assert.assertEquals(1, af.getTag().getFieldCount());
        }

        //TXXX
        {
            tag = (ID3v24Tag) af.getTag();
            frame = new ID3v24Frame(ID3v24Frames.FRAME_ID_USER_DEFINED_INFO);
            ((FrameBodyTXXX) frame.getBody()).setText("UserDefined");
            tag.setFrame(frame);
            ID3v24Frame txxxFrame = (ID3v24Frame) tag.getFrame("TXXX");
            FrameBodyTXXX fb = (FrameBodyTXXX) txxxFrame.getBody();
            Assert.assertEquals("", fb.getDescription());
            Assert.assertEquals("UserDefined", fb.getText());
            //Change description, cant do this with common interface
            fb.setDescription("test");
            //Because has different description the following setField will addField another txxx rather than overwriting the first one
            af.getTag().setField(af.getTag().createField(FieldKey.MUSICBRAINZ_ARTISTID, "abcdef-ghijklmn"));
            Assert.assertEquals(2, ((List) tag.getFrame("TXXX")).size());
            //Now adding TXXX with same id so gets overwritten
            af.getTag().setField(af.getTag().createField(FieldKey.MUSICBRAINZ_ARTISTID, "abcfffff"));
            Assert.assertEquals(2, ((List) tag.getFrame("TXXX")).size());

            //Try deleting some of these
            tag.removeFrameOfType("TXXX");
            Assert.assertNull(tag.getFrame("TXXX"));

            af.getTag().setField(af.getTag().createField(FieldKey.MUSICBRAINZ_ARTISTID, "abcdef-ghijklmn"));
            ((ID3v24Tag) af.getTag()).deleteField(FieldKey.MUSICBRAINZ_ARTISTID);
            Assert.assertNull(tag.getFrame("TXXX"));

        }

        //UFID
        {
            tag = (ID3v24Tag) af.getTag();
            frame = new ID3v24Frame(ID3v24Frames.FRAME_ID_UNIQUE_FILE_ID);
            ((FrameBodyUFID) frame.getBody()).setOwner("owner");
            tag.setFrame(frame);
            ID3v24Frame ufidFrame = (ID3v24Frame) tag.getFrame("UFID");
            FrameBodyUFID fb = (FrameBodyUFID) ufidFrame.getBody();
            Assert.assertEquals("owner", fb.getOwner());

            //Because has different owner the following setField will addField another ufid rather than overwriting the first one
            af.getTag().setField(af.getTag().createField(FieldKey.MUSICBRAINZ_TRACK_ID, "abcdef-ghijklmn"));
            Assert.assertEquals(2, ((List) tag.getFrame("UFID")).size());
            //Now adding UFID with same owner so gets overwritten
            af.getTag().setField(af.getTag().createField(FieldKey.MUSICBRAINZ_TRACK_ID, "abcfffff"));
            Assert.assertEquals(2, ((List) tag.getFrame("UFID")).size());

            //Try deleting some of these
            tag.removeFrame("UFID");
            Assert.assertNull(tag.getFrame("UFID"));
        }

        //ULST
        {
            tag = (ID3v24Tag) af.getTag();
            frame = new ID3v24Frame(ID3v24Frames.FRAME_ID_UNSYNC_LYRICS);
            ((FrameBodyUSLT) frame.getBody()).setDescription("lyrics1");
            tag.setFrame(frame);
            ID3v24Frame usltFrame = (ID3v24Frame) tag.getFrame("USLT");
            FrameBodyUSLT fb = (FrameBodyUSLT) usltFrame.getBody();
            Assert.assertEquals("lyrics1", fb.getDescription());

            //Because has different desc the following setField will addField another uslt rather than overwriting the first one
            af.getTag().setField(af.getTag().createField(FieldKey.LYRICS, "abcdef-ghijklmn"));
            Assert.assertEquals(2, ((List) tag.getFrame("USLT")).size());
            Assert.assertEquals(2, af.getTag().getFields(FieldKey.LYRICS).size());
            frame = (ID3v24Frame) ((List) tag.getFrame("USLT")).get(1);
            Assert.assertEquals("", ((FrameBodyUSLT) frame.getBody()).getDescription());
            //Now adding USLT with same description so gets overwritten
            af.getTag().setField(af.getTag().createField(FieldKey.LYRICS, "abcfffff"));
            Assert.assertEquals(2, ((List) tag.getFrame("USLT")).size());
            Assert.assertEquals(2, af.getTag().getFields(FieldKey.LYRICS).size());

        }

        //POPM TODO not a supported FieldKey yet
        {
            tag = (ID3v24Tag) af.getTag();
            frame = new ID3v24Frame(ID3v24Frames.FRAME_ID_POPULARIMETER);
            ((FrameBodyPOPM) frame.getBody()).setEmailToUser("paultaylor@jthink.net");
            tag.setFrame(frame);
            ID3v24Frame popmFrame = (ID3v24Frame) tag.getFrame("POPM");
            FrameBodyPOPM fb = (FrameBodyPOPM) popmFrame.getBody();
            Assert.assertEquals("paultaylor@jthink.net", fb.getEmailToUser());
        }

    }

   
    @Test
    public void testIterator() throws Exception
    {
        File orig = new File("testdata", "test26.mp3");
        if (!orig.isFile())
        {
            return;
        }

        Exception e = null;
        File testFile = AbstractTestCase.copyAudioToTmp("test26.mp3");
        MP3File mp3File = new MP3File(testFile);


        Assert.assertEquals(0, mp3File.getID3v2Tag().getFieldCount());
        Iterator<TagField> i = mp3File.getID3v2Tag().getFields();
        Assert.assertFalse(i.hasNext());
        try
        {
            i.next();
        }
        catch(Exception ex)
        {
            e=ex;
        }
        Assert.assertTrue(e instanceof NoSuchElementException);

        mp3File.getID3v2Tag().addField(FieldKey.ALBUM,"album");
        Assert.assertEquals(1, mp3File.getID3v2Tag().getFieldCount());
        i = mp3File.getID3v2Tag().getFields();

        //Should be able to iterate without actually having to call isNext() first
        i.next();

        //Should be able to call hasNext() without it having any effect
        i = mp3File.getID3v2Tag().getFields();
        Assert.assertTrue(i.hasNext());
        Object o = i.next();
        Assert.assertTrue(o instanceof ID3v23Frame);
        Assert.assertEquals("album", ((AbstractFrameBodyTextInfo) (((ID3v23Frame) o).getBody())).getFirstTextValue());

        try
        {
            i.next();
        }
        catch(Exception ex)
        {
            e=ex;
        }
        Assert.assertTrue(e instanceof NoSuchElementException);
        Assert.assertFalse(i.hasNext());

        //Empty frame map and force adding of empty list
        mp3File.getID3v2Tag().frameMap.clear();
        mp3File.getID3v2Tag().frameMap.put("TXXX",new ArrayList());
        Assert.assertEquals(0, mp3File.getID3v2Tag().getFieldCount());

        //Issue #236
        //i = mp3File.getID3v2Tag().getFields();
        //assertFalse(i.hasNext());
    }

    /**
     * Currently genres are written to and from v2 tag as is, the decoding from genre number to string has to be done manually
     */
    @Test
    public void testGenres()
    {
        Exception ex = null;
        try
        {
            File testFile = AbstractTestCase.copyAudioToTmp("testV1.mp3", new File("testBasicWrite.mp3"));
            org.jaudiotagger.audio.AudioFile audioFile = org.jaudiotagger.audio.AudioFileIO.read(testFile);
            org.jaudiotagger.tag.Tag newTag = audioFile.getTag();
            Assert.assertTrue(newTag == null);
            if (audioFile.getTag() == null)
            {
                audioFile.setTag(new ID3v23Tag());
                newTag = audioFile.getTag();
            }

            //Write literal String
            newTag.setField(FieldKey.GENRE,"Rock");
            audioFile.commit();
            audioFile = org.jaudiotagger.audio.AudioFileIO.read(testFile);
            newTag = audioFile.getTag();
            //..and read back
            Assert.assertEquals("Rock", newTag.getFirst(FieldKey.GENRE));

            //Write Code
            newTag.setField(FieldKey.GENRE,"(17)");
            audioFile.commit();
            audioFile = org.jaudiotagger.audio.AudioFileIO.read(testFile);
            newTag = audioFile.getTag();
            //..and read back
            Assert.assertEquals("Rock", newTag.getFirst(FieldKey.GENRE));

        }
        catch (Exception e)
        {
            ex = e;
            ex.printStackTrace();
        }
        Assert.assertNull(ex);
    }

    @Test
    public void testRemoveFrameOfType()
    {
        File orig = new File("testdata", "test30.mp3");
        if (!orig.isFile())
        {
            return;
        }

        Exception exceptionCaught = null;
        File testFile = AbstractTestCase.copyAudioToTmp("test30.mp3");
        MP3File mp3file = null;
        try
        {
            mp3file = new MP3File(testFile);
            //deleteField multiple frames starting make change to file
            ((ID3v24Tag) mp3file.getID3v2Tag()).removeFrameOfType("PRIV");

        }
        catch (Exception e)
        {
            e.printStackTrace();
            exceptionCaught = e;
        }
        Assert.assertNull(exceptionCaught);


    }
}
