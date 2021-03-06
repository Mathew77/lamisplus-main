package org.lamisplus.modules.base.service;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lamisplus.modules.base.BaseApplication;
import org.lamisplus.modules.base.controller.apierror.EntityNotFoundException;
import org.lamisplus.modules.base.domain.dto.ApplicationCodesetDTO;
import org.lamisplus.modules.base.domain.entity.ApplicationCodeset;
import org.lamisplus.modules.base.repository.ApplicationCodesetRepository;
import org.lamisplus.modules.base.service.ApplicationCodesetService;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


import static org.assertj.core.api.Assertions.assertThat;

@Profile("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes= BaseApplication.class)
public class ApplicationCodeSetServiceTest {
    @Autowired
    private ApplicationCodesetService applicationCodesetService;

    @MockBean
    private ApplicationCodesetRepository applicationCodesetRepository;

    @Before
    public void setUp() {
        ApplicationCodeset applicationCodeset= new ApplicationCodeset();
        applicationCodeset.setId(1L);
        applicationCodeset.setDisplay("FEMALE");
        applicationCodeset.setCodesetGroup("GENDER");
        applicationCodeset.setVersion("1");
        applicationCodeset.setLanguage("en");
        applicationCodeset.setDisplay("Male-male");
        applicationCodeset.setActive(1);
        applicationCodeset.setCode("34792968-e3df-4954-bd97-2f60d8fc1393");

        ApplicationCodeset applicationCodeset1= new ApplicationCodeset();
        applicationCodeset1.setId(2L);
        applicationCodeset1.setDisplay("MALE");
        applicationCodeset1.setCodesetGroup("GENDER");
        applicationCodeset1.setVersion("1");
        applicationCodeset1.setLanguage("en");
        applicationCodeset1.setDisplay("Male-test");
        applicationCodeset1.setActive(1);
        applicationCodeset1.setCode("34792968-e3df-4954-bd97-2f60d8fc1393");


        List<ApplicationCodeset> allApplicationCodeset = Arrays.asList(applicationCodeset, applicationCodeset1);

        Mockito.when(applicationCodesetRepository.findByDisplay(applicationCodeset.getDisplay())).thenReturn(applicationCodeset);
        Mockito.when(applicationCodesetRepository.findByDisplay("wrong_name")).thenReturn(null);

        Mockito.when(applicationCodesetRepository.findById(applicationCodeset.getId())).thenReturn(Optional.of(applicationCodeset));
        Mockito.when(applicationCodesetRepository.findById(applicationCodeset1.getId())).thenReturn(Optional.of(applicationCodeset1));
        Mockito.when(applicationCodesetRepository.findById(-99L)).thenReturn(null);

        Mockito.when(applicationCodesetRepository.findAll()).thenReturn(allApplicationCodeset);
    }

    @Test
    public void whenValidCodeSet_thenApplicationCodesetShouldBeFound() {
        String codeset = "GENDER";
        ApplicationCodesetDTO applicationCodesetDTO = applicationCodesetService.getApplicationCodeset(1L);

        assertThat(applicationCodesetDTO.getCodesetGroup()).isEqualTo(codeset);
    }

    @Test
    @DisplayName("method should throw exception")
    public void whenInValidName_thenApplicationCodesetShouldNotBeFound() {
        Long id = 3L;
        Throwable thrown = assertThrows(EntityNotFoundException.class, () -> applicationCodesetService.getApplicationCodeset(id));
        assertThat(thrown.getMessage(), is("ApplicationCodeset was not found for parameters {Display:="+id+"}"));
        System.out.println(thrown.getCause()+"class is" +thrown.getClass());
        //assertThat(thrown.getClass(), instanceOf(NoSuchElementException.class));


        //verifyFindByDisplayIsCalledOnce("wrong_name");
    }


    private void verifyFindByDisplayIsCalledOnce(String display) {
        Mockito.verify(applicationCodesetRepository, VerificationModeFactory.times(1)).findByDisplay(display);
        Mockito.reset(applicationCodesetRepository);
    }

    private void verifyFindByIdIsCalledOnce() {
        Mockito.verify(applicationCodesetRepository, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
        Mockito.reset(applicationCodesetRepository);
    }

    private void verifyFindAllApplicationCodesetIsCalledOnce() {
        Mockito.verify(applicationCodesetRepository, VerificationModeFactory.times(1)).findAll();
        Mockito.reset(applicationCodesetRepository);
    }
}
