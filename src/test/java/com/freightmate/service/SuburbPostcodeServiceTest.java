package com.freightmate.service;

import com.freightmate.dto.SuburbNameInfo;
import com.freightmate.dto.SuburbPostcodeInfo;
import com.freightmate.entity.SuburbPostcodeEntity;
import com.freightmate.entity.SuburbPostcodeId;
import com.freightmate.exception.ResourceNotFoundException;
import com.freightmate.repository.SuburbPostcodeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author Hrishikesh.Lotekar
 * @implNote SuburbPostcodeServiceTest Service Test Class
 */
@ExtendWith(MockitoExtension.class)
 class SuburbPostcodeServiceTest {

    @Mock
    private SuburbPostcodeRepository suburbPostcodeRepository;

    @InjectMocks
    private SuburbPostcodeServiceImpl suburbPostcodeService;

    /**
     * Description : Test when a valid Suburb is provided for existing Postcode.
     */
    @Test
    void testGetPostcodesBySuburb_ValidSuburbWithExistingPostcodes() throws ResourceNotFoundException {
        // Arrange
        String validSuburb = "Test Suburb";
        List<Integer> existingPostcodes = new ArrayList<>();
        existingPostcodes.add(2000);
        when(suburbPostcodeRepository.findPostcodesBySuburbName(validSuburb)).thenReturn(existingPostcodes);

        // Act
        Optional<List<Integer>> result = suburbPostcodeService.getPostcodesBySuburb(validSuburb);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(existingPostcodes, result.get());
    }


    /**
     * Description : Test when a valid Suburb is provided for no existing Postcode.
     */
    @Test
    void testGetPostcodesBySuburb_ValidSuburbWithNoExistingPostcodes() {
        // Arrange
        String validSuburb = "Test Suburb";
        when(suburbPostcodeRepository.findPostcodesBySuburbName(validSuburb)).thenReturn(new ArrayList<>());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> suburbPostcodeService.getPostcodesBySuburb(validSuburb));
    }

    /**
     * Description : Test when a valid postcode with existing suburbs is provided
     */

    @Test
    void testGetSuburbsByPostcode_ValidPostcodeWithExistingSuburbs() throws ResourceNotFoundException {
        // Arrange
        int validPostcode = 3000;
        List<SuburbPostcodeEntity> existingSuburbs = new ArrayList<>();
        existingSuburbs.add(new SuburbPostcodeEntity(new SuburbPostcodeId("Suburb1", validPostcode)));
        existingSuburbs.add(new SuburbPostcodeEntity(new SuburbPostcodeId("Suburb2", validPostcode)));
        when(suburbPostcodeRepository.findByIdPostcode(validPostcode)).thenReturn(existingSuburbs);

        // Act
        List<SuburbNameInfo> result = suburbPostcodeService.getSuburbsByPostcode(validPostcode);

        // Assert
        assertNotNull(result);
        assertEquals(existingSuburbs.size(), result.size());
        assertEquals(existingSuburbs.get(0).getId().getSuburbName(), result.get(0).getSuburbName());
        assertEquals(existingSuburbs.get(1).getId().getSuburbName(), result.get(1).getSuburbName());
    }

    /**
     * Description : Test when a valid postcode with no existing suburbs is provided
     */
    @Test
    void testGetSuburbsByPostcode_ValidPostcodeWithNoExistingSuburbs() {
        // Arrange
        int validPostcode = 3000;
        when(suburbPostcodeRepository.findByIdPostcode(validPostcode)).thenReturn(new ArrayList<>());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> suburbPostcodeService.getSuburbsByPostcode(validPostcode));
    }

    /**
     * Description : Test adding a new suburb and postcode combination that doesn't exist.
     */
    @Test
    void testAddSuburbPostcode_ValidCombination() {
        // Arrange
        SuburbPostcodeInfo validDTO = new SuburbPostcodeInfo("Valid Suburb", 3000);
        SuburbPostcodeEntity entityToSave = new SuburbPostcodeEntity(new SuburbPostcodeId(validDTO.getSuburbName(), validDTO.getPostcode()));
        when(suburbPostcodeRepository.existsById(entityToSave.getId())).thenReturn(false);
        when(suburbPostcodeRepository.save(entityToSave)).thenReturn(entityToSave);

        // Act
        SuburbPostcodeInfo result = suburbPostcodeService.addSuburbPostcode(validDTO);

        // Assert
        assertNotNull(result);
        assertEquals(validDTO.getSuburbName(), result.getSuburbName());
        assertEquals(validDTO.getPostcode(), result.getPostcode());
    }

    /**
     * Description : Test adding a new suburb and postcode combination that already exists.
     */
    @Test
    void testAddSuburbPostcode_ExistingCombination() {
        // Arrange
        SuburbPostcodeInfo validDTO = new SuburbPostcodeInfo("Valid Suburb", 3000);
        SuburbPostcodeEntity entityToSave = new SuburbPostcodeEntity(new SuburbPostcodeId(validDTO.getSuburbName(), validDTO.getPostcode()));
        when(suburbPostcodeRepository.existsById(entityToSave.getId())).thenReturn(true);

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> suburbPostcodeService.addSuburbPostcode(validDTO));
    }

    /**
     * Description : Test when a valid postcode is provided it returns a list.
     */
    @Test
     void testGetSuburbsByPostcode_ValidPostcodeNonEmptyList_Success() {
        // given - precondition
        int validPostcode = 9999;

        List<SuburbPostcodeEntity> mockSuburbEntities = new ArrayList<>();

        // Create instances of SuburbPostcodeEntity and set the embedded id
        SuburbPostcodeEntity suburbEntity1 = new SuburbPostcodeEntity();
        SuburbPostcodeId id1 = new SuburbPostcodeId();
        id1.setSuburbName("NORTH POLE");
        id1.setPostcode(validPostcode);
        suburbEntity1.setId(id1);

        // Add the created entities to the mockSuburbEntities list
        mockSuburbEntities.add(suburbEntity1);

        // Stub repository behavior
        when(suburbPostcodeRepository.findByIdPostcode(validPostcode)).thenReturn(mockSuburbEntities);

        // Act
        List<SuburbNameInfo> result = suburbPostcodeService.getSuburbsByPostcode(validPostcode);

        // Assert
        assertThat(result, is(not(empty())));
        assertThat(result.size(), is(equalTo(mockSuburbEntities.size())));

        // Verify repository method call
        verify(suburbPostcodeRepository).findByIdPostcode(validPostcode);
        verifyNoMoreInteractions(suburbPostcodeRepository);
    }


    /**
     * Description : Test when an invalid postcode (less than 200) is provided.
     */
    @Test
     void testGetSuburbsByPostcodeWithInvalidPostcodeLessThan200() {
        // Arrange
        int invalidPostcode = 100;
        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> suburbPostcodeService.getSuburbsByPostcode(invalidPostcode));
    }

    /**
     * Description : Test when an invalid postcode (greater than 9999) is provided.
     */
    @Test
     void testGetSuburbsByPostcodeWithInvalidPostcodeGreaterThan9999() {
        // Arrange
        int invalidPostcode = 10000;
        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> suburbPostcodeService.getSuburbsByPostcode(invalidPostcode));
    }

    /**
     * Description : Test when an empty suburb name is provided.
     */
    @Test
     void testGetPostcodesBySuburbWithEmptySuburbName() {
        // Arrange
        String suburbName = "";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> suburbPostcodeService.getPostcodesBySuburb(suburbName));
        verifyNoInteractions(suburbPostcodeRepository);
    }

    /**
     * Description : Test when a null suburb name is provided.
     */
    @Test
     void testGetPostcodesBySuburbWithNullSuburbName() {
        // Arrange
        String suburbName = null;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> suburbPostcodeService.getPostcodesBySuburb(suburbName));
        verifyNoInteractions(suburbPostcodeRepository);
    }



    /**
     * Description : Test converting a SuburbPostcodeEntity to SuburbPostcodeInfo DTO.
     */
    @Test
     void testConvertEntityToDTO() {
        // Arrange
        SuburbPostcodeEntity entity = new SuburbPostcodeEntity();
        SuburbPostcodeId entityId = new SuburbPostcodeId("SampleSuburb", 12345);
        entity.setId(entityId);
        // Act
        SuburbPostcodeInfo dto = SuburbPostcodeInfo.convertEntityToDTO(entity);
        // Assert
        assertEquals("SampleSuburb", dto.getSuburbName());
        assertEquals(12345, dto.getPostcode());
    }

    /**
     * Description : Test converting a SuburbPostcodeEntity to SuburbNameInfo DTO.
     */
    @Test
     void testConvertEntityToDTOSuburbNameInfo() {
        // Arrange
        SuburbPostcodeEntity entity = new SuburbPostcodeEntity();
        SuburbPostcodeId entityId = new SuburbPostcodeId("SampleSuburb", 12345);
        entity.setId(entityId);
        // Act
        SuburbNameInfo dto = SuburbNameInfo.convertEntityToDTOSuburbNameInfo(entity);
        // Assert
        assertEquals("SampleSuburb", dto.getSuburbName());
    }

    /**
     * Description : Test converting a SuburbPostcodeInfo DTO to SuburbPostcodeEntity Entity
     */
    @Test
     void testConvertDTOToEntity() {
        // Arrange
        SuburbPostcodeInfo dto = SuburbPostcodeInfo.builder()
                .suburbName("SampleSuburb")
                .postcode(12345)
                .build();
        // Act
        SuburbPostcodeEntity entity = SuburbPostcodeInfo.convertDTOToEntity(dto);
        // Assert
        assertNotNull(entity);
        assertEquals("SampleSuburb", entity.getId().getSuburbName());
        assertEquals(12345, entity.getId().getPostcode());
    }


}
