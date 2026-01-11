# TODO for Aligning Code with Specified Flow

## Frontend API Files
- [x] Create departmentApi.ts
- [x] Create sectionApi.ts
- [x] Create workingDayApi.ts
- [x] Create timeSlotApi.ts
- [x] Create facultyApi.ts
- [x] Create roomApi.ts
- [x] Create rulesApi.ts (for generation constraints)

## Frontend Pages
- [x] Create DepartmentPage.tsx (CRUD)
- [x] Create SectionPage.tsx (CRUD)
- [x] Create WorkingDayPage.tsx (CRUD)
- [x] Create TimeSlotPage.tsx (CRUD) - Fixed import issue
- [x] Create FacultyPage.tsx (CRUD)
- [x] Create RoomPage.tsx (CRUD)
- [x] Create RulesPage.tsx (for setting constraints)
- [x] Rename ClassTimetablePage.tsx to SectionTimetablePage.tsx
- [x] Create FacultyTimetablePage.tsx
- [x] Create PrintableTimetablePage.tsx

## Frontend Updates
- [ ] Update App.tsx to include navigation and routes for all pages in order: Department, Sections, Working Days, Periods, Subjects, Faculty, Rooms, Rules, Generate, Views (with sub-links for section, faculty, printable)
- [ ] Update GenerateTimetablePage.tsx to use dropdowns for department, semester, sections by fetching data

## Testing
- [ ] Test all new pages and APIs
- [ ] Verify the flow works end-to-end
