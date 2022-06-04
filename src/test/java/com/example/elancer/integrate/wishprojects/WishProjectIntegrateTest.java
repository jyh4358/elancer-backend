package com.example.elancer.integrate.wishprojects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

//public class WishProjectIntegrateTest extends IntegrateBaseTest {
//    @Autowired
//    private ProjectRepository projectRepository;
//
//    @Autowired
//    private WishProjectRepository wishProjectRepository;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Autowired
//    private JwtTokenService jwtTokenService;
//
//
//    @DisplayName("프로젝트찜 생성 통합테스트")
//    @Test
//    public void 프로젝트찜_생성() throws Exception {
//        //given
//        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);
//        MemberLoginResponse memberLoginResponse = LoginHelper.로그인(freelancer.getUserId(), jwtTokenService);
//
//        FreelancerProfile freelancerProfile = freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.ETC));
//
//        Project project = projectRepository.save(new Project(ProjectBackGround.BLACK));
//
//        WishProjectSaveRequest wishProjectSaveRequest = new WishProjectSaveRequest(project.getNum());
//
//        //when
//        mockMvc.perform(post(WishProjectControllerPath.WISH_PROJECT_SAVE)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
//                        .content(objectMapper.writeValueAsString(wishProjectSaveRequest)))
//                .andExpect(status().isCreated())
//                .andDo(print()
//                );
//
//        //then
//        List<WishProject> wishProjects = wishProjectRepository.findAll();
//        Assertions.assertThat(wishProjects).hasSize(1);
//    }
//
//    @DisplayName("프로젝트찜 삭제 통합테스트")
//    @Test
//    public void 프로젝트찜_삭제() throws Exception {
//        //given
//        Freelancer freelancer = FreelancerHelper.프리랜서_생성(freelancerRepository, passwordEncoder);
//        MemberLoginResponse memberLoginResponse = LoginHelper.로그인(freelancer.getUserId(), jwtTokenService);
//
//        freelancerProfileRepository.save(new FreelancerProfile("greeting", freelancer, PositionType.ETC));
//
//        Project project = projectRepository.save(new Project(ProjectBackGround.BLACK));
//
//        WishProject wishProject = wishProjectRepository.save(WishProject.createWishProject(freelancer, project));
//
//        WishProjectDeleteRequest wishProjectDeleteRequest = new WishProjectDeleteRequest(wishProject.getNum());
//
//        //when
//        mockMvc.perform(delete(WishProjectControllerPath.WISH_PROJECT_DELETE)
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .header(JwtTokenProvider.AUTHORITIES_KEY, memberLoginResponse.getAccessToken())
//                        .content(objectMapper.writeValueAsString(wishProjectDeleteRequest)))
//                .andExpect(status().isOk())
//                .andDo(print()
//                );
//
//        //then
//        List<WishProject> wishProjects = wishProjectRepository.findAll();
//        Assertions.assertThat(wishProjects).hasSize(0);
//    }
//
//    @AfterEach
//    void tearDown() {
//        databaseClean.clean();
//    }
//}
