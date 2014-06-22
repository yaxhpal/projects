#
# Generated Makefile - do not edit!
#
# Edit the Makefile in the project folder instead (../Makefile). Each target
# has a -pre and a -post target defined where you can add customized code.
#
# This makefile implements configuration specific macros and targets.


# Environment
MKDIR=mkdir
CP=cp
GREP=grep
NM=nm
CCADMIN=CCadmin
RANLIB=ranlib
CC=gcc
CCC=g++
CXX=g++
FC=gfortran
AS=as

# Macros
CND_PLATFORM=GNU-Linux-x86
CND_DLIB_EXT=so
CND_CONF=Default
CND_DISTDIR=dist
CND_BUILDDIR=build

# Include project Makefile
include Makefile

# Object Directory
OBJECTDIR=${CND_BUILDDIR}/${CND_CONF}/${CND_PLATFORM}

# Object Files
OBJECTFILES= \
	${OBJECTDIR}/FreewayWindow.o \
	${OBJECTDIR}/callbacks.o \
	${OBJECTDIR}/main.o \
	${OBJECTDIR}/maniac.o \
	${OBJECTDIR}/police.o \
	${OBJECTDIR}/sports.o \
	${OBJECTDIR}/traffic.o \
	${OBJECTDIR}/truck.o \
	${OBJECTDIR}/vehicle.o \
	${OBJECTDIR}/vehicle_list.o


# C Compiler Flags
CFLAGS=

# CC Compiler Flags
CCFLAGS=
CXXFLAGS=

# Fortran Compiler Flags
FFLAGS=

# Assembler Flags
ASFLAGS=

# Link Libraries and Options
LDLIBSOPTIONS=`pkg-config --libs gtk+-2.0`  

# Build Targets
.build-conf: ${BUILD_SUBPROJECTS}
	"${MAKE}"  -f nbproject/Makefile-${CND_CONF}.mk ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/freeway_1

${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/freeway_1: ${OBJECTFILES}
	${MKDIR} -p ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}
	${LINK.cc} -o ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/freeway_1 ${OBJECTFILES} ${LDLIBSOPTIONS}

${OBJECTDIR}/FreewayWindow.o: FreewayWindow.cc 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -g `pkg-config --cflags gtk+-2.0`   -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/FreewayWindow.o FreewayWindow.cc

${OBJECTDIR}/callbacks.o: callbacks.cc 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -g `pkg-config --cflags gtk+-2.0`   -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/callbacks.o callbacks.cc

${OBJECTDIR}/main.o: main.cc 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -g `pkg-config --cflags gtk+-2.0`   -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/main.o main.cc

${OBJECTDIR}/maniac.o: maniac.cc 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -g `pkg-config --cflags gtk+-2.0`   -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/maniac.o maniac.cc

${OBJECTDIR}/police.o: police.cc 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -g `pkg-config --cflags gtk+-2.0`   -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/police.o police.cc

${OBJECTDIR}/sports.o: sports.cc 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -g `pkg-config --cflags gtk+-2.0`   -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/sports.o sports.cc

${OBJECTDIR}/traffic.o: traffic.cc 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -g `pkg-config --cflags gtk+-2.0`   -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/traffic.o traffic.cc

${OBJECTDIR}/truck.o: truck.cc 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -g `pkg-config --cflags gtk+-2.0`   -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/truck.o truck.cc

${OBJECTDIR}/vehicle.o: vehicle.cc 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -g `pkg-config --cflags gtk+-2.0`   -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/vehicle.o vehicle.cc

${OBJECTDIR}/vehicle_list.o: vehicle_list.cc 
	${MKDIR} -p ${OBJECTDIR}
	${RM} "$@.d"
	$(COMPILE.cc) -g `pkg-config --cflags gtk+-2.0`   -MMD -MP -MF "$@.d" -o ${OBJECTDIR}/vehicle_list.o vehicle_list.cc

# Subprojects
.build-subprojects:

# Clean Targets
.clean-conf: ${CLEAN_SUBPROJECTS}
	${RM} -r ${CND_BUILDDIR}/${CND_CONF}
	${RM} ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/freeway_1

# Subprojects
.clean-subprojects:

# Enable dependency checking
.dep.inc: .depcheck-impl

include .dep.inc
